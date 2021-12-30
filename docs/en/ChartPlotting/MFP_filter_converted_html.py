
fileInputName = 'MFP_graphing_functions_new.html'
fileOutputName = 'MFP_graphing_functions.html'
totalTxt = ''
with open(fileInputName, encoding='utf-8') as f:
    lines = f.readlines()
    for line in lines:
        totalTxt += line

totalTxt = totalTxt.replace('''<p class=MsoNormal  style="margin-top:5.0000pt;margin-right:0.0000pt;margin-bottom:12.0000pt;
margin-left:0.0000pt;mso-margin-top-alt:auto;mso-pagination:widow-orphan;" ><span style="mso-spacerun:'yes';font-family:Garamond;color:rgb(0,0,0);
font-size:10.0000pt;mso-font-kerning:0.0000pt;background:rgb(192,192,192);
mso-highlight:rgb(192,192,192);" >''', '\n<fakep style="font-family:verdana;color:blue">')

def filter_func(originalTxt: str, str2Check: str) -> str:
    idx = 0
    isToDelete = False
    filteredTxt = ''
    while idx < len(originalTxt):
        if originalTxt[idx:idx + len(str2Check)] == str2Check:
            isToDelete = True
        elif isToDelete and originalTxt[idx:idx + 1] == '>':
            isToDelete = False
        elif not isToDelete:
            filteredTxt += originalTxt[idx]
        idx += 1
    return filteredTxt

totalTxt = filter_func(totalTxt, '<span')
totalTxt = filter_func(totalTxt, '</span')
totalTxt = filter_func(totalTxt, '<b ')
totalTxt = filter_func(totalTxt, '<o:p')
totalTxt = filter_func(totalTxt, '</o:p')
totalTxt = filter_func(totalTxt, '<font')
totalTxt = filter_func(totalTxt, '</font')

def remove_decorates_func(originalTxt: str, str2Check: str) -> str:
    idx = 0
    isToDelete = False
    filteredTxt = ''
    while idx < len(originalTxt):
        if originalTxt[idx:idx + len(str2Check)] == str2Check:
            filteredTxt += (str2Check if (idx == 0) or (originalTxt[idx - 1] == '\n') else ('\n' + str2Check))
            idx += len(str2Check)
            isToDelete = True
        elif isToDelete and originalTxt[idx:idx + 1] == '>':
            filteredTxt += '>'
            idx += 1
            isToDelete = False
        else:
            if not isToDelete:
                filteredTxt += originalTxt[idx]
            idx += 1
    return filteredTxt

totalTxt = remove_decorates_func(totalTxt, '<p')
totalTxt = remove_decorates_func(totalTxt, '<table')
totalTxt = remove_decorates_func(totalTxt, '<td')

totalTxt = totalTxt.replace('<b>','')
totalTxt = totalTxt.replace('</b>','')
totalTxt = totalTxt.replace('<table>', '<table border="1" style="font-family:times;color:green;">')
totalTxt = totalTxt.replace('<p>', '<p style="font-family:verdana">')
totalTxt = totalTxt.replace('<h2 style="margin-left', '\n<h3 style="margin-left')
totalTxt = totalTxt.replace('</h2>', '</h3>')
firstCloseH3 = totalTxt.find('</h3>', 0)
totalTxt = totalTxt[:firstCloseH3] + '</h2>' + totalTxt[firstCloseH3 + len('</h3>'):]
totalTxt = totalTxt.replace('<fakep', '<p')
totalTxt = totalTxt.replace('//', '<i style="font-family:verdana;color:green;">//')
totalTxt = totalTxt.replace('<![if !supportLists]>', '')
totalTxt = totalTxt.replace('<![endif]>', '')

lastComment = 0
while True:
    lastComment = totalTxt.find('//', lastComment + 2)
    if lastComment == -1:
        break
    lastEndP = totalTxt.find('</p>', lastComment)
    totalTxt = totalTxt[:lastEndP] + '</i>' + totalTxt[lastEndP:]

totalTxt = remove_decorates_func(totalTxt, '<h3')

with open(fileOutputName, "w", encoding='utf-8') as o:
    o.write(totalTxt)