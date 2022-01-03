import sys

fileInputName = sys.argv[1]
fileOutputName = sys.argv[2]
headTxt = '''<html>
	<head>
		<title>MFP language Help</title>
	</head>
	<body style="background-color:white;">
		<h2 style="color:blue">MFP language Help: Mathematic Analysis and Calculation Functions</h2>
'''

totalTxt = ''
with open(fileInputName, encoding='utf-8') as f:
    lines = f.readlines()
    for line in lines:
        totalTxt += line
        
lastComment = totalTxt.find('<p ', 0)
totalTxt = headTxt + totalTxt[lastComment:]

totalTxt = totalTxt.replace('''<p class=MsoNormal  align=justify  style="margin-bottom:12.0000pt;text-align:justify;text-justify:inter-ideograph;" ><span style="mso-spacerun:'yes';font-family:Garamond;color:rgb(0,0,0);
font-size:10.0000pt;background:rgb(192,192,192);mso-highlight:rgb(192,192,192);" >''', '\n<fakep style="font-family:verdana;color:blue">')
totalTxt = totalTxt.replace('''<p class=MsoNormal  style="margin-bottom:12.0000pt;" ><span style="mso-spacerun:'yes';font-family:Garamond;color:rgb(0,0,0);
font-size:10.0000pt;background:rgb(192,192,192);mso-highlight:rgb(192,192,192);" >''', '\n<fakep style="font-family:verdana;color:blue">')

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
totalTxt = totalTxt.replace('</div><!--EndFragment--></body>', '\n</body>')

allTextLns = totalTxt.split('\n')
inHelpBlock = False
totalTxt = ''
for ln in allTextLns:
    if ln.lower() == '<p style="font-family:verdana;color:blue">help</p>':
        thisLn = remove_decorates_func(ln, '<p')
        thisLn = thisLn.replace('<p>', '<p style="font-family:verdana;color:green">')
        totalTxt += thisLn + '\n'
        inHelpBlock = True
    elif ln.lower() == '<p style="font-family:verdana;color:blue">endh</p>':
        thisLn = remove_decorates_func(ln, '<p')
        thisLn = thisLn.replace('<p>', '<p style="font-family:verdana;color:green">')
        totalTxt += thisLn + '\n'
        inHelpBlock = False
    else:
        if inHelpBlock:
            thisLn = remove_decorates_func(ln, '<p')
            thisLn = thisLn.replace('<p>', '<p style="font-family:verdana;color:green">')
            totalTxt += thisLn + '\n'
        else:
            totalTxt += ln + '\n'

lastComment = 0
while True:
    lastComment = totalTxt.find('//', lastComment + 2)
    if lastComment == -1:
        break
    lastEndP = totalTxt.find('</p>', lastComment)
    totalTxt = totalTxt[:lastEndP] + '</i>' + totalTxt[lastEndP:]

totalTxt = remove_decorates_func(totalTxt, '<h3')
totalTxt = totalTxt.replace('<h2>Summary', '<h3>Summary')

with open(fileOutputName, "w", encoding='utf-8') as o:
    o.write(totalTxt)