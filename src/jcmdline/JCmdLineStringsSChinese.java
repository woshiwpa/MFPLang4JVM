/*
 * MFP project, JCmdLineStringsSChinese.java : Designed and developed by Tony Cui in 2021
 */
package jcmdline;

/**
 *
 * @author tonyc
 */
public class JCmdLineStringsSChinese extends JCmdLineStrings {

    @Override
    public String get_hello() {
        return "Hello World, ActivityMain!";
    }

    @Override
    public String get_app_name() {
        return "可编程科学计算器";
    }

    @Override
    public String get_alert_dialog_ok() {
        return "确定";
    }

    @Override
    public String get_alert_dialog_cancel() {
        return "取消";
    }

    @Override
    public String get_menu_cmdline() {
        return "命令提示符";
    }

    @Override
    public String get_menu_plotgraph() {
        return "绘制二维曲线";
    }

    @Override
    public String get_menu_integrate() {
        return "积分计算";
    }

    @Override
    public String get_menu_history() {
        return "历史记录";
    }

    @Override
    public String get_menu_settings() {
        return "设置";
    }

    @Override
    public String get_menu_help() {
        return "帮助";
    }

    @Override
    public String get_setting_number_format_prompt() {
        return "设置数值精度";
    }

    @Override
    public String get_setting_record_length_prompt() {
        return "设置记录长度";
    }

    @Override
    public String get_setting_extreme_value_prompt() {
        return "设置科学计数";
    }

    @Override
    public String get_whats_new() {
        return "更新和增加的功能";
    }

    @Override
    public String get_do_not_show_whats_new_again() {
        return "下一次启动时不再显示。";
    }

    @Override
    public String get_welcome_message() {
        return "输入help或表达式如：3 + log(4.1 / avg(1,5,-3))，然后按“计算”";
    }

    @Override
    public String get_EXPERROR_NO_EXPRESSION() {
        return "没有表达式！";
    }

    @Override
    public String get_EXPERROR_MULTIPLE_EXPRESSIONS() {
        return "多个表达式！";
    }

    @Override
    public String get_EXPERROR_LACK_OPERATOR_BETWEEN_TWO_OPERANDS() {
        return "两个计算数之间缺少计算符！";
    }

    @Override
    public String get_EXPERROR_NUM_CAN_NOT_HAVE_TWO_DECIMAL_POINT() {
        return "两个小数点！";
    }

    @Override
    public String get_EXPERROR_NUM_DO_NOT_ACCORD_DECIMAL_NUM_WRITING_STANDARD() {
        return "数字格式不对！";
    }

    @Override
    public String get_EXPERROR_OPERATOR_NOT_EXIST() {
        return "不存在的操作符！";
    }

    @Override
    public String get_EXPERROR_UNMATCHED_RIGHTPARENTHESE() {
        return "右括号没有对应的左括号！";
    }

    @Override
    public String get_EXPERROR_UNMATCHED_LEFTPARENTHESE() {
        return "左括号没有对应的右括号！";
    }

    @Override
    public String get_EXPERROR_WRONG_OPERAND_TYPE() {
        return "不正确的操作数类型！";
    }

    @Override
    public String get_EXPERROR_INCORRECT_ANSWER_OF_POWER_FUNCTION_OPERANDS() {
        return "次方（power）函数的参数不正确！";
    }

    @Override
    public String get_EXPERROR_INCORRECT_BINARY_OPERATOR() {
        return "不正确的二元操作符！";
    }

    @Override
    public String get_EXPERROR_INCORRECT_MONADIC_OPERATOR() {
        return "不正确的一元操作符！";
    }

    @Override
    public String get_EXPERROR_LACK_OPERAND() {
        return "缺少操作数！";
    }

    @Override
    public String get_EXPERROR_CAN_NOT_IDENTIFIED_CHARACTER() {
        return "字符无法识别！";
    }

    @Override
    public String get_EXPERROR_DATATYPE_IS_NOT_DEFINED() {
        return "数据类型没有定义！";
    }

    @Override
    public String get_EXPERROR_CAN_NOT_CHANGE_A_NOTEXIST_DATUM_TO_ANY_OTHER_DATATYPE() {
        return "无法将不存在的类型变成其它任何数据类型！";
    }

    @Override
    public String get_EXPERROR_NEW_DATATYPE_IS_NOT_DEFINED() {
        return "新数据类型没有定义！";
    }

    @Override
    public String get_EXPERROR_INTEGER_OVERFLOW() {
        return "整数溢出！";
    }

    @Override
    public String get_EXPERROR_DOUBLE_OVERFLOW() {
        return "浮点数溢出！";
    }

    @Override
    public String get_EXPERROR_CHANGE_FROM_DOUBLE_TO_INTEGER_OVERFLOW() {
        return "将浮点数转换为整数时溢出！";
    }

    @Override
    public String get_EXPERROR_OPERAND_OF_FACTORIAL_MAY_BE_TOO_LARGE() {
        return "阶乘参数值过大！";
    }

    @Override
    public String get_EXPERROR_DIVISOR_CAN_NOT_BE_ZERO() {
        return "被零除！";
    }

    @Override
    public String get_EXPERROR_OPERAND_OF_FACTORIAL_CAN_NOT_LESS_THAN_ZERO() {
        return "负的阶乘参数值！";
    }

    @Override
    public String get_EXPERROR_OPERAND_OF_BIT_OPERATION_MUST_BE_GREATER_THAN_ZERO() {
        return "位操作数必须为正数！";
    }

    @Override
    public String get_EXPERROR_INCORRECT_NUM_OF_PARAMETER() {
        return "参数数目不对！";
    }

    @Override
    public String get_EXPERROR_INVALID_PARAMETER_RANGE() {
        return "不正确的参数范围！";
    }

    @Override
    public String get_EXPERROR_INVALID_PARAMETER_TYPE() {
        return "不正确的参数类型！";
    }

    @Override
    public String get_EXPERROR_UNDEFINED_FUNCTION() {
        return "没有定义的函数！";
    }

    @Override
    public String get_EXPERROR_UNDEFINED_VARIABLE() {
        return "没有定义的变量！";
    }

    @Override
    public String get_go_button() {
        return "计算!";
    }

    @Override
    public String get_numbers_operators_button_long() {
        return "数字\n操作符";
    }

    @Override
    public String get_common_functions_button_long() {
        return "通用函数";
    }

    @Override
    public String get_more_functions_button_long() {
        return "更多函数";
    }

    @Override
    public String get_numbers_operators_button_short() {
        return "1+2*3";
    }

    @Override
    public String get_common_functions_button_short() {
        return "f(x)";
    }

    @Override
    public String get_more_functions_button_short() {
        return "f()...";
    }

    @Override
    public String get_left_button() {
        return "向左";
    }

    @Override
    public String get_right_button() {
        return "向右";
    }

    @Override
    public String get_eraze_button() {
        return "删除";
    }

    @Override
    public String get_help_button() {
        return "帮助";
    }

    @Override
    public String get_x_help_info() {
        return "X待解变量x的名字或者其他待解变量比如x1，xy，xx等的名字的一部分。";
    }

    @Override
    public String get_y_help_info() {
        return "Y待解变量y的名字或者其他待解变量比如y3，xy，yyyy等的名字的一部分。";
    }

    @Override
    public String get_z_help_info() {
        return "Z待解变量z的名字或者其他待解变量比如z2，zx，zzz等的名字的一部分。";
    }

    @Override
    public String get_assign_help_info() {
        return "赋值操作符。本操作符将一个数值赋给一个变量。比如，x = 3 + 4将数值7赋给变量x。";
    }

    @Override
    public String get_equal_help_info() {
        return "等于号。本符号意味着本符号左边的表达式和符号右边的表达式有同样的数值。比如，x + 1 == 7 + 3意味着x + 1和7 + 3有同样的数值，也就是10。通过这个表达式，我们可以解出x的值为9。";
    }

    @Override
    public String get_parenthesis_help_info() {
        return "左括号。位于一对括号中的表达式有更高的运算优先级。比如，x * (y + 3)。";
    }

    @Override
    public String get_closeparenthesis_help_info() {
        return "右括号。位于一对括号中的表达式有更高的运算优先级。比如，x * (y + 3)。";
    }

    @Override
    public String get_squarebracket_help_info() {
        return "左方括号。左方括号为一个数组的定义的开始。比如，[[1,2,3],[4,5,6]]定义了一个2*3矩阵，第一行为包括3个元素（也就是1，2和3）的数组，第二行也为包括3个元素（也就是4，5和6）的数组。该矩阵本身也为一个数组包括2个数组元素。";
    }

    @Override
    public String get_closesquarebracket_help_info() {
        return "右方括号。右方括号为一个数组的定义的结束。比如，[[1,2,3],[4,5,6]]定义了一个2*3矩阵，第一行为包括3个元素（也就是1，2和3）的数组，第二行也为包括3个元素（也就是4，5和6）的数组。该矩阵本身也为一个数组包括2个数组元素。";
    }

    @Override
    public String get_comma_help_info() {
        return "逗号将数组中的每一个元素分隔开，比如，数组[2,3,4,5]有4个元素被3个逗号分隔开。";
    }

    @Override
    public String get_plus_help_info() {
        return "正数或加号（加号支持复数，矩阵和字符串）。";
    }

    @Override
    public String get_minus_help_info() {
        return "负数或减号（减号支持复数和矩阵）。";
    }

    @Override
    public String get_multiplication_help_info() {
        return "乘号（支持复数和矩阵）。";
    }

    @Override
    public String get_division_help_info() {
        return "除号（支持复数和矩阵）。";
    }

    @Override
    public String get_leftdivision_help_info() {
        return "左除号（主要用于矩阵相除，比如计算Ax=b中的x时，x=A\\b，注意第一个操作数是除数，第二个是被除数，如果除数不是矩阵，和除号的功能完全一样）。";
    }

    @Override
    public String get_power_help_info() {
        return "次方。注意如果两个运算数都可以为复数。";
    }

    @Override
    public String get_exclaimation_help_info() {
        return "如果位于操作数左边是否操作符，如果位于操作数有变是阶乘操作符。注意操作数必须是非负并且自动转换为整数。";
    }

    @Override
    public String get_transpose_help_info() {
        return "二维矩阵转秩。如果操作数是一维向量，返回一个二维矩阵，如果操作数不是矩阵，返回操作数本身。";
    }

    @Override
    public String get_doublequote_help_info() {
        return "双引号，用于标示字符串。";
    }

    @Override
    public String get_percentage_help_info() {
        return "百分号。例如，403.77% = 4.0377。";
    }

    @Override
    public String get_bit_and_help_info() {
        return "位与操作符，注意操作数必须为非负整数。";
    }

    @Override
    public String get_bit_or_help_info() {
        return "位或操作符，注意操作数必须为非负整数。";
    }

    @Override
    public String get_bit_xor_help_info() {
        return "位异或操作符，注意操作数必须为非负整数。";
    }

    @Override
    public String get_bit_not_help_info() {
        return "位否操作符，注意操作数必须为非负整数。";
    }

    @Override
    public String get_image_i_help_info() {
        return "用于复数计算的单位虚数值。";
    }

    @Override
    public String get_pi_constant_help_info() {
        return "圆周率。";
    }

    @Override
    public String get_e_constant_help_info() {
        return "自然对数e。注意e同时也用于科学计数，比如1.23e-002。";
    }

    @Override
    public String get_null_constant_help_info() {
        return "NULL（空值）。";
    }

    @Override
    public String get_true_constant_help_info() {
        return "布尔值true。";
    }

    @Override
    public String get_false_constant_help_info() {
        return "布尔值false。";
    }

    @Override
    public String get_inf_constant_help_info() {
        return "数值无穷大。";
    }

    @Override
    public String get_infi_constant_help_info() {
        return "虚数值无穷大。";
    }

    @Override
    public String get_nan_constant_help_info() {
        return "没有定义的数值，比如0/0。";
    }

    @Override
    public String get_nani_constant_help_info() {
        return "没有定义的虚数值。";
    }

    @Override
    public String get_no_quick_help_info() {
        return "没有快速帮助信息对应于";
    }

    @Override
    public String get_calculator_settings() {
        return "计算器设置";
    }

    @Override
    public String get_digits_shown() {
        return "位数字";
    }

    @Override
    public String get_let_calculator_decide() {
        return "让计算器决定";
    }

    @Override
    public String get_never_sci_notation() {
        return "不使用科学计数";
    }

    @Override
    public String get_always_sci_notation() {
        return "总是使用科学计数";
    }

    @Override
    public String get_if_log10_abs_result() {
        return "仅当log10(abs(结果)) >= ";
    }

    @Override
    public String get_records_shown() {
        return " 条记录";
    }

    @Override
    public String get_enable_btn_press_vibration_prompt() {
        return "按计算器按钮时震动";
    }

    @Override
    public String get_external_storage_mnt_folder_prompt() {
        return "存储设备（比如SD卡）所在系统文件夹:";
    }

    @Override
    public String get_app_folder_prompt() {
        return "应用程序数据在存储设备上的文件夹位于：";
    }

    @Override
    public String get_script_folder_prompt() {
        return "用户定义的函数存储于存储设备文件夹：";
    }

    @Override
    public String get_chart_folder_prompt() {
        return "使用者生成的图片存放于存储设备文件夹：";
    }

    @Override
    public String get_settings_saved() {
        return "设置已经保存";
    }

    @Override
    public String get_select_record_title() {
        return "计算记录";
    }

    @Override
    public String get_no_records() {
        return "没有记录！";
    }

    @Override
    public String get_select_input_type() {
        return "请选择表达式或者结果以输入";
    }

    @Override
    public String get_error_answer_shown() {
        return "错误";
    }

    @Override
    public String get_return_nothing_answer_shown() {
        return "没有返回值";
    }

    @Override
    public String get_variables_declared_shown() {
        return "申明变量：";
    }

    @Override
    public String get_calculator_help() {
        return "计算器帮助";
    }

    @Override
    public String get_cmd_line_title() {
        return "命令提示符";
    }

    @Override
    public String get_cmd_line_welcome_message() {
        return "请输入表达式或者help加空格加函数名，然后按回车键。";
    }

    @Override
    public String get_command_prompt() {
        return "$>";
    }

    @Override
    public String get_last_cmd() {
        return "上一条指令";
    }

    @Override
    public String get_new_script() {
        return "新程序文件";
    }

    @Override
    public String get_manage_scripts() {
        return "管理程序文件";
    }

    @Override
    public String get_view_charts() {
        return "观看图形";
    }

    @Override
    public String get_error() {
        return "错误！";
    }

    @Override
    public String get_warning() {
        return "警告！";
    }

    @Override
    public String get_ok() {
        return "确定";
    }

    @Override
    public String get_cancel() {
        return "取消";
    }

    @Override
    public String get_yes() {
        return "是";
    }

    @Override
    public String get_no() {
        return "否";
    }

    @Override
    public String get_close() {
        return "关闭";
    }

    @Override
    public String get_script_file_manager_title() {
        return "程序文件管理器";
    }

    @Override
    public String get_chart_file_manager_title() {
        return "图形文件管理器";
    }

    @Override
    public String get_file_manager_title() {
        return "文件管理器";
    }

    @Override
    public String get_script_editor_title() {
        return "程序编辑器";
    }

    @Override
    public String get_script_file_saved() {
        return "文件已经保存";
    }

    @Override
    public String get_file_folder_icon() {
        return "文件或者文件夹图标";
    }

    @Override
    public String get_file_manager_menu_new() {
        return "新建";
    }

    @Override
    public String get_file_manager_menu_open() {
        return "打开";
    }

    @Override
    public String get_file_manager_menu_save() {
        return "保存";
    }

    @Override
    public String get_file_manager_menu_rename() {
        return "重命名";
    }

    @Override
    public String get_file_manager_menu_delete() {
        return "删除";
    }

    @Override
    public String get_file_manager_invalid_path() {
        return "非法路径！";
    }

    @Override
    public String get_example_scripts_not_loaded() {
        return "example文件夹用于存放开发示例，该文件夹以及其子文件夹中的所有程序将不会被加载！";
    }

    @Override
    public String get_file_manager_new_file_type_script_file() {
        return "新的程序文件";
    }

    @Override
    public String get_file_manager_new_file_type_folder() {
        return "新文件夹";
    }

    @Override
    public String get_file_manager_new_file_prompt() {
        return "请输入文件名：";
    }

    @Override
    public String get_file_manager_new_file_title() {
        return "创建新的文件或文件夹";
    }

    @Override
    public String get_file_manager_new_file_failed_msg() {
        return "无法创建新文件或文件夹！";
    }

    @Override
    public String get_file_manager_file_rename_title() {
        return "重命名";
    }

    @Override
    public String get_file_manager_file_new_name_prompt() {
        return "请输入新的文件名：";
    }

    @Override
    public String get_file_manager_file_rename_failed_msg() {
        return "无法重命名！";
    }

    @Override
    public String get_file_manager_file_delete_title() {
        return "删除";
    }

    @Override
    public String get_file_manager_file_delete_confirm() {
        return "你确定要删除";
    }

    @Override
    public String get_file_manager_file_delete_failed_msg() {
        return "无法删除！";
    }

    @Override
    public String get_file_editor_file_open_fail() {
        return "无法打开文件！";
    }

    @Override
    public String get_file_not_found() {
        return "找不到文件！";
    }

    @Override
    public String get_file_editor_file_io_error() {
        return "IO错误！";
    }

    @Override
    public String get_file_editor_file_changed() {
        return "文件已经被更改，是否保存？";
    }

    @Override
    public String get_file_editor_new_file() {
        return "新文件";
    }

    @Override
    public String get_file_editor_file() {
        return "文件";
    }

    @Override
    public String get_file_editor_inside() {
        return "位于";
    }

    @Override
    public String get_file_editor_menu_new() {
        return "新建";
    }

    @Override
    public String get_file_editor_menu_open() {
        return "打开";
    }

    @Override
    public String get_file_editor_menu_save() {
        return "保存";
    }

    @Override
    public String get_file_editor_menu_save_as() {
        return "另存为";
    }

    @Override
    public String get_file_editor_menu_goto_line() {
        return "跳转至";
    }

    @Override
    public String get_file_editor_goto_line() {
        return "跳转至行";
    }

    @Override
    public String get_plot_2d_title() {
        return "绘制2维曲线图";
    }

    @Override
    public String get_graph_name_prompt() {
        return "图像名：";
    }

    @Override
    public String get_graph_title_prompt() {
        return "图像标题：";
    }

    @Override
    public String get_fill_surface() {
        return "是否填充表面";
    }

    @Override
    public String get_is_surface_grid() {
        return "不填充表面（仅绘制网格）";
    }

    @Override
    public String get_graph_Xtitle_prompt() {
        return "X轴标题：";
    }

    @Override
    public String get_graph_Ytitle_prompt() {
        return "Y轴标题：";
    }

    @Override
    public String get_graph_Ztitle_prompt() {
        return "Z轴标题：";
    }

    @Override
    public String get_graph_Rtitle_prompt() {
        return "R轴标题：";
    }

    @Override
    public String get_graph_show_grid_chkbox_prompt() {
        return "显示\n网格";
    }

    @Override
    public String get_add_curve_btn_text() {
        return "添加曲线";
    }

    @Override
    public String get_clear_all_btn_text() {
        return "清除所有";
    }

    @Override
    public String get_generate_chart_btn_text() {
        return "观看";
    }

    @Override
    public String get_curve_title_prompt() {
        return "标题：";
    }

    @Override
    public String get_curve_color_prompt() {
        return "颜色：";
    }

    @Override
    public String get_curve_point_style_prompt() {
        return "点的形状：";
    }

    @Override
    public String get_curve_show_line_chkbox_prompt() {
        return "显示连接线";
    }

    @Override
    public String get_max_color_prompt() {
        return "最大值所对应的颜色（正反面）：";
    }

    @Override
    public String get_max_color_value_prompt() {
        return "最大值为：";
    }

    @Override
    public String get_min_color_prompt() {
        return "最小值所对应的颜色（正反面）：";
    }

    @Override
    public String get_min_color_value_prompt() {
        return "最小值为：";
    }

    @Override
    public String get_t_from_prompt() {
        return "t：从";
    }

    @Override
    public String get_t_to_prompt() {
        return "到";
    }

    @Override
    public String get_t_step_prompt() {
        return "间隔";
    }

    @Override
    public String get_u_from_prompt() {
        return "u：从";
    }

    @Override
    public String get_u_to_prompt() {
        return "到";
    }

    @Override
    public String get_u_step_prompt() {
        return "间隔";
    }

    @Override
    public String get_v_from_prompt() {
        return "v：从";
    }

    @Override
    public String get_v_to_prompt() {
        return "到";
    }

    @Override
    public String get_v_step_prompt() {
        return "间隔";
    }

    @Override
    public String get_delete_curve_btn_text() {
        return "删除曲线";
    }

    @Override
    public String get_clear_curve_btn_text() {
        return "清除曲线定义";
    }

    @Override
    public String get_circle_point_style() {
        return "圆形";
    }

    @Override
    public String get_triangle_point_style() {
        return "三角形";
    }

    @Override
    public String get_square_point_style() {
        return "正方形";
    }

    @Override
    public String get_diamond_point_style() {
        return "菱形";
    }

    @Override
    public String get_x_point_style() {
        return "对角叉";
    }

    @Override
    public String get_point_point_style() {
        return "点";
    }

    @Override
    public String get_black_color() {
        return "黑色";
    }

    @Override
    public String get_blue_color() {
        return "蓝色";
    }

    @Override
    public String get_cyan_color() {
        return "青色";
    }

    @Override
    public String get_dkgray_color() {
        return "深灰色";
    }

    @Override
    public String get_gray_color() {
        return "灰色";
    }

    @Override
    public String get_green_color() {
        return "绿色";
    }

    @Override
    public String get_ltgray_color() {
        return "浅灰色";
    }

    @Override
    public String get_magenta_color() {
        return "紫红色";
    }

    @Override
    public String get_orange_color() {
        return "橘黄色";
    }

    @Override
    public String get_pink_color() {
        return "粉红色";
    }

    @Override
    public String get_red_color() {
        return "红色";
    }

    @Override
    public String get_transparent_color() {
        return "透明";
    }

    @Override
    public String get_white_color() {
        return "白色";
    }

    @Override
    public String get_yellow_color() {
        return "黄色";
    }

    @Override
    public String get_graph_settings_wrong() {
        return "图像设置有问题所以无法绘制！";
    }

    @Override
    public String get_graph_file_cannot_be_read() {
        return "在阅读图像文件时发生错误！";
    }

    @Override
    public String get_graph_file_cannot_be_saved() {
        return "在保存图像文件时发生错误！";
    }

    @Override
    public String get_please_wait() {
        return "请等待";
    }

    @Override
    public String get_calculating_chart_data() {
        return "正在进行绘制图形所需要的计算...";
    }

    @Override
    public String get_integrate_title() {
        return "积分";
    }

    @Override
    public String get_integrated_expr_prompt() {
        return "被积分\n表达式：";
    }

    @Override
    public String get_you_want_to_calculate() {
        return "您想要计算：";
    }

    @Override
    public String get_x_variable_name() {
        return "第一个积分变量的名字：";
    }

    @Override
    public String get_y_variable_name() {
        return "第二个积分变量的名字：";
    }

    @Override
    public String get_z_variable_name() {
        return "第三个积分变量的名字：";
    }

    @Override
    public String get_variable_from() {
        return "从";
    }

    @Override
    public String get_variable_to() {
        return "到";
    }

    @Override
    public String get_variable_number_of_steps() {
        return "积分步数";
    }

    @Override
    public String get_integ_settings_wrong() {
        return "积分输入有问题，积分无法计算！";
    }

    @Override
    public String get_calculating_integrating_result() {
        return "正在积分积分结果...";
    }

    @Override
    public String get_integrating_result() {
        return "积分结果";
    }

    @Override
    public String get_cannot_create_app_folder() {
        return "无法生成数据文件夹，使用者将无法保存自己开发的函数和浏览生成的图片！";
    }

    @Override
    public String get_cannot_create_script_folder() {
        return "无法生成函数程序文件夹，使用者将无法保存自己开发的函数！";
    }

    @Override
    public String get_cannot_create_chart_folder() {
        return "无法生成图片文件夹，使用者将无法浏览生成的图片！";
    }

    @Override
    public String get_function_name() {
        return "函数名";
    }

    @Override
    public String get_function_info() {
        return "函数帮助信息";
    }

    @Override
    public String get_all_functions() {
        return "所有函数";
    }

    @Override
    public String get_builtin_functions() {
        return "内置函数";
    }

    @Override
    public String get_predefined_functions() {
        return "预定义函数";
    }

    @Override
    public String get_integer_operation_functions() {
        return "整数操作函数";
    }

    @Override
    public String get_logic_functions() {
        return "逻辑函数";
    }

    @Override
    public String get_statistic_and_stochastic_functions() {
        return "统计和随机函数";
    }

    @Override
    public String get_trigononmetric_functions() {
        return "三角函数";
    }

    @Override
    public String get_exponential_and_logarithmic_functions() {
        return "指数对数和次方函数";
    }

    @Override
    public String get_complex_number_functions() {
        return "复数操作函数";
    }

    @Override
    public String get_system_functions() {
        return "系统函数";
    }

    @Override
    public String get_array_or_matrix_functions() {
        return "数组和矩阵函数";
    }

    @Override
    public String get_graphic_functions() {
        return "图形函数";
    }

    @Override
    public String get_expression_and_integration_functions() {
        return "表达式和积分函数";
    }

    @Override
    public String get_string_functions() {
        return "字符串函数";
    }

    @Override
    public String get_hyperbolic_trigononmetric_functions() {
        return "双曲三角函数";
    }

    @Override
    public String get_sorting_functions() {
        return "排序函数";
    }

    @Override
    public String get_others_functions() {
        return "其他函数";
    }

    @Override
    public String get_exception() {
        return "异常！";
    }

    @Override
    public String get_back_to_normal() {
        return "回复正常。";
    }

    @Override
    public String get_unmounted_external_storage() {
        return "无法找到外接存储器（比如SD卡）！";
    }

    @Override
    public String get_mounting_external_storage() {
        return "正在寻找并装载外接存储器。注意在此过程中计算可能会出现中断。";
    }

    @Override
    public String get_mounted_external_storage() {
        return "找到并已装载外接存储器。";
    }

    // added for Java program
    @Override
    public String get_wrong_file_type() {
        return "错误的文件类型！";
    }

    @Override
    public String get_cannot_open_file() {
        return "无法打开文件！";
    }

    @Override
    public String get_cannot_find_file() {
        return "无法找到文件！";
    }

    @Override
    public String get_error_in_analyzing_help_requirement() {
        return "分析帮助命令时出现错误！";
    }

    @Override
    public String get_select_folder() {
        return "选择文件夹";
    }

    @Override
    public String get_copy_to_main_window() {
        return "拷贝到主窗口";
    }

    @Override
    public String get_app_quick_introduction() {
        return "本软件是一个类似Matlab的，进行数学分析的有力工具。";
    }

    @Override
    public String get_product_version() {
        return "版本：";
    }

    @Override
    public String get_product_vendor() {
        return "开发商：";
    }

    @Override
    public String get_product_homepage() {
        return "主页：";
    }

    @Override
    public String get_menu_file() {
        return "文件";
    }

    @Override
    public String get_menu_reload_lib() {
        return "刷新程序库";
    }

    @Override
    public String get_menu_edit() {
        return "编辑";
    }

    @Override
    public String get_menu_interrupt_cmd() {
        return "终止运行中的任务";
    }

    @Override
    public String get_menu_wrap_line() {
        return "自动换行";
    }

    @Override
    public String get_menu_select_all() {
        return "全选";
    }

    @Override
    public String get_menu_copy() {
        return "拷贝";
    }

    @Override
    public String get_menu_paste() {
        return "粘贴";
    }

    @Override
    public String get_menu_clear() {
        return "清屏";
    }

    @Override
    public String get_menu_exit() {
        return "退出";
    }

    @Override
    public String get_menu_tools() {
        return "工具";
    }

    @Override
    public String get_menu_howto() {
        return "如何...";
    }

    @Override
    public String get_menu_get_constant() {
        return "获取常数值";
    }

    @Override
    public String get_menu_convert_unit() {
        return "进行单位转换";
    }

    @Override
    public String get_menu_calc_polynomial_roots() {
        return "计算多项式的根";
    }

    @Override
    public String get_menu_plot_multixy_graph() {
        return "绘制二维图形";
    }

    @Override
    public String get_menu_plot_polar_graph() {
        return "绘制极坐标图形";
    }

    @Override
    public String get_menu_plot_multixyz_graph() {
        return "绘制三维图形";
    }

    @Override
    public String get_menu_view_chart() {
        return "打开图像文件";
    }

    @Override
    public String get_menu_content() {
        return "内容";
    }

    @Override
    public String get_menu_about() {
        return "关于...";
    }

    @Override
    public String get_about() {
        return "关于";
    }

    @Override
    public String get_integration_input_title() {
        return "积分设置";
    }

    @Override
    public String get_XY_chart_def_title() {
        return "绘制二维图形";
    }

    @Override
    public String get_Polar_chart_def_title() {
        return "绘制极坐标图形";
    }

    @Override
    public String get_XYZ_chart_def_title() {
        return "绘制三维图形";
    }

    @Override
    public String get_settings_dialog_title() {
        return "设置";
    }

    @Override
    public String get_select_user_defined_functions_folder_prompt() {
        return "选择用于存放用户自定义函数的文件夹：";
    }

    @Override
    public String get_select_charts_folder_prompt() {
        return "选择用于存放图像文件的文件夹：";
    }

    @Override
    public String get_select_btn_text() {
        return "选择";
    }

    @Override
    public String get_setting_scientific_notation_thresh_prompt() {
        return "使用科学计数法显示计算结果如果结果大于：";
    }

    @Override
    public String get_single_line_integrated_expr_prompt() {
        return "被积分表达式：";
    }

    @Override
    public String get_indefinite_integral() {
        return "不定积分";
    }

    @Override
    public String get_single_integral() {
        return "一次积分";
    }

    @Override
    public String get_double_integral() {
        return "二次积分";
    }

    @Override
    public String get_triple_integral() {
        return "三次积分";
    }

    @Override
    public String get_wait_for_calculation() {
        return "计算任务正在运行，待它执行完成之后，";
    }

    @Override
    public String get_help_get_constant_info() {
        return "请您运行\"help get_constant\"以获取更多信息！";
    }

    @Override
    public String get_help_convert_unit_info() {
        return "请您运行\"help convert_unit\"以获取更多信息！";
    }

    @Override
    public String get_help_calc_polynomial_roots_info() {
        return "请您运行\"help roots\"以获取更多信息！";
    }

    @Override
    public String get_interrupt_running_cmd() {
        return "Ctrl-D被按下，运行中的任务被终止！";
    }

    @Override
    public String get_session_starts() {
        return "命令集开始执行";
    }

    @Override
    public String get_session_returns() {
        return "命令集返回";
    }

    @Override
    public String get_cannot_find_asset_files() {
        return "Asset目录缺失。您可能无法使用一些函数或者帮助文档。";
    }

    @Override
    public String get_find_corrupted_asset_files() {
        return "无法生成一些程序文件。您可能无法使用一些函数或者帮助文档。";
    }

    @Override
    public String get_starting() {
        return "正在启动...";
    }

    @Override
    public String get_unzipping_asset_files() {
        return "正在解压缩程序文件...";
    }

    @Override
    public String get_loading_functions() {
        return "正在装载函数库...";
    }

    @Override
    public String get_receive_following_errors() {
        return "在启动过程中收到如下错误信息：";
    }

    @Override
    public String get_cannot_load_opengl_libs() {
        return "Open GL库文件无法装载，这将造成三维图像无法绘制！";
    }

    @Override
    public String get_ogl_chart_cannot_plot_lack_system_libs() {
        return "由于缺少和系统平台相关的库文件造成三维图像无法绘制，请和开发人员联系以获取您的系统平台的库文件。";
    }

    @Override
    public String get_multiXYZ_config() {
        return "设置3D图形";
    }

    @Override
    public String get_multiXYZ_settings_zoom_prompt() {
        return "全图缩放";
    }

    @Override
    public String get_multiXYZ_settings_x_zoom_prompt() {
        return "x轴缩放";
    }

    @Override
    public String get_multiXYZ_settings_y_zoom_prompt() {
        return "y轴缩放";
    }

    @Override
    public String get_multiXYZ_settings_z_zoom_prompt() {
        return "z轴缩放";
    }

    @Override
    public String get_multiXYZ_settings_x_shift_prompt() {
        return "沿x轴向平移";
    }

    @Override
    public String get_multiXYZ_settings_y_shift_prompt() {
        return "沿y轴向平移";
    }

    @Override
    public String get_multiXYZ_settings_z_shift_prompt() {
        return "沿z轴向平移";
    }

    @Override
    public String get_multiXYZ_settings_x_rotate_prompt() {
        return "绕水平轴转动（角度）";
    }

    @Override
    public String get_multiXYZ_settings_y_rotate_prompt() {
        return "绕垂直轴转动（角度）";
    }

    @Override
    public String get_multiXYZ_settings_z_rotate_prompt() {
        return "逆时针转动（角度）";
    }

    @Override
    public String get_input_invalid_number() {
        return "请输入一个数！";
    }

    @Override
    public String get_input_number_invalid_range() {
        return "输入的数不是在合法的范围，它不应该";
    }

    @Override
    public String get_apply() {
        return "应用";
    }

    @Override
    public String get_3DExpr_config() {
        return "设置基于表达式的3D图形";
    }

    @Override
    public String get_XYExpr_config() {
        return "设置基于表达式的2D图形";
    }

    @Override
    public String get_PolarExpr_config() {
        return "设置基于表达式的极坐标图形";
    }

    @Override
    public String get_detect_singular_points() {
        return "侦测奇点";
    }

    @Override
    public String get_app_in_a_read_only_folder() {
        return "请您将整个AnMath目录拷贝至一个可读写的文件夹中然后再运行基于JAVA的可编程科学计算器。";
    }

    @Override
    public String get_miss_asset_or_app_in_a_zipped_folder_or_unmapped_usb() {
        return "请确定没有文件缺失（比如缺少assets.zip）并拷贝整个AnMath目录至一个可读写的位子然后\n再运行本软件。";
    }

    @Override
    public String get_app_in_a_read_only_folder_full() {
        return "基于JAVA的可编程科学计算器可能位于一个只读目录中。请您将整个AnMath\n目录拷贝至一个可读写的文件夹中然后再运行基于JAVA的可编程科学计算器。";
    }

    @Override
    public String get_miss_asset_or_app_in_a_zipped_folder_or_unmapped_usb_full() {
        return "基于JAVA的可编程科学计算器可能缺少一些文件（比如assets.zip）或者可能位于一个\n压缩文件夹中或者移动设备的USB驱动程序不支持从移动设备的存储器中运行可执行文件。\n请确定没有文件缺失并拷贝整个AnMath目录至一个可读写的位子然后再运行本软件。";
    }

    @Override
    public String get_plot_chart_variable_range_prompt() {
        return "用plot_exprs等函数绘制图形时的变量范围：";
    }

    @Override
    public String get_plot_chart_variable_range_to_prompt() {
        return "到";
    }

    @Override
    public String get_Polar_chart_r_range_note() {
        return "注意：极坐标半径范围总是从0到一个正数值。";
    }

    @Override
    public String get_Polar_chart_angle_range_note() {
        return "注意：极坐标幅角是基于";
    }

    @Override
    public String get_degree() {
        return "角度";
    }

    @Override
    public String get_radius() {
        return "弧度";
    }

    @Override
    public String get_not_show_axis_and_title() {
        return "不显示坐标轴和标题";
    }

    @Override
    public String get_not_show_axis() {
        return "不显示坐标轴";
    }

    @Override
    public String get_not_show_title() {
        return "不显示标题";
    }

    @Override
    public String get_application_name_prompt() {
        return "请输入应用名。应用名长度不超过32字节。这意味着32个英文字母或者最多（有可能少于）16个中文汉字。";
    }

    @Override
    public String get_application_pkg_id_prompt() {
        return "请输入应用包ID。ID必须正好20个字符长。ID必须是唯一的，否则您无法在谷歌商店或者其他任何应用发布站点发布您的应用。ID只能包含英文字母，数字和点。点不能是第一个或者最后一个字符，也不能有两个点相连。数字不能为第一个字符，也不能紧跟在点的后面。两点之间不能有多于10个字母或者数字。例如：com.cyzapps.AnMFPApp 。";
    }

    @Override
    public String get_application_version_prompt() {
        return "请输入您的应用的版本代码（左边的输入）和版本号（右边的输入）。应用的版本代码是一个包含10个英文字符或者数字或者点的字符串，比如1.0.0.0001；应用的版本号则是一个正整数不大于65535。";
    }

    @Override
    public String get_application_icon_selector_prompt() {
        return "请选择您的应用图标。应用的图标文件必须是一个正方形，不小于128乘以128的png文件。如果您不做选择，默认的图标将会被自动地赋予您的应用。选中的图标将会被显示在选择按钮上。";
    }

    @Override
    public String get_application_working_folder_prompt() {
        return "请输入您的应用在SD卡上的工作目录";
    }

    @Override
    public String get_application_working_folder_hint() {
        return "如果您没有输入，缺省工作目录将会是包ID的最后一部分。比如包ID为com.cyzapps.MFPApp，则默认的工作目录为SD卡上的MFPApp目录。";
    }

    @Override
    public String get_selected_icon_invalid() {
        return "应用的图标文件必须是一个正方形，不小于128乘以128的png文件。";
    }

    @Override
    public String get_select() {
        return "选择";
    }

    @Override
    public String get_application_description_prompt() {
        return "请简要描述您的应用，比如，您的应用做些什么，需要用户输入什么。注意这不是用户手册，所以必须简短（不多于256个字符）。它将在您的应用启动时显示。您也可以忽略此项输入。";
    }

    @Override
    public String get_go_to_next() {
        return "继续";
    }

    @Override
    public String get_mfpapp_prog_name() {
        return "创建MFP应用";
    }

    @Override
    public String get_long_click_to_open() {
        return "长按打开文件夹或者文件";
    }

    @Override
    public String get_app_name_invalid() {
        return "应用名不符合要求！";
    }

    @Override
    public String get_app_pkg_id_invalid() {
        return "应用包ID不符合要求！";
    }

    @Override
    public String get_app_ver_str_invalid() {
        return "应用版本字符串不符合要求！";
    }

    @Override
    public String get_app_ver_code_invalid() {
        return "应用版本号不符合要求！";
    }

    @Override
    public String get_app_working_folder_invalid() {
        return "应用工作目录名称非法！";
    }

    @Override
    public String get_help_use_default_app_description_or_type() {
        return "选择自动生成应用描述作为当用户点击帮助菜单时的帮助页面。自动生成的帮助页面将包含函数帮助以及您的email和主页。如果您不选择自动生成应用描述，您可以将自定义的应用帮助写在下面的文本框中：";
    }

    @Override
    public String get_function_name_prompt() {
        return "函数名";
    }

    @Override
    public String get_function_name_invalid() {
        return "非法函数名！";
    }

    @Override
    public String get_function_description_prompt() {
        return "请简要描述您的函数，比如，您的函数做些什么，需要用户输入什么。注意这不是用户手册，所以必须简短（不多于1024个字符）。它将在您的应用启动时显示。您也可以忽略此项输入。";
    }

    @Override
    public String get_function_description_invalid() {
        return "函数描述过长！";
    }

    @Override
    public String get_function_help() {
        return "函数帮助";
    }

    @Override
    public String get_information_about_parameter() {
        return "关于该参数的信息";
    }

    @Override
    public String get_parameter_default_value() {
        return "该参数的默认值";
    }

    @Override
    public String get_with_optional_params() {
        return "使用可选参数";
    }

    @Override
    public String get_param_is_a_string() {
        return "参数是一个字符串";
    }

    @Override
    public String get_param_needs_no_input() {
        return "参数无需用户输入";
    }

    @Override
    public String get_add() {
        return "添加";
    }

    @Override
    public String get_delete() {
        return "删除";
    }

    @Override
    public String get_delete_all() {
        return "删除所有";
    }

    @Override
    public String get_please_input_apk_name() {
        return "请输入您的apk文件名。Apk文件将会在您的AnMath\\apks目录中生成。";
    }

    @Override
    public String get_select_apk_signiture_key() {
        return "选择密匙：";
    }

    @Override
    public String get_please_input_keystore_name() {
        return "请输入储存密匙文件名。该文件将会在于您的AnMath\\signkeys目录中生成。";
    }

    @Override
    public String get_please_input_keystore_password() {
        return "储存密匙文件密码";
    }

    @Override
    public String get_please_input_password_again() {
        return "再次输入密码";
    }

    @Override
    public String get_please_input_key_name() {
        return "密匙名";
    }

    @Override
    public String get_please_input_key_valid_period() {
        return "有效年限";
    }

    @Override
    public String get_please_input_key_password() {
        return "密匙密码";
    }

    @Override
    public String get_please_input_your_personal_information() {
        return "请输入您的个人或公司信息。这些信息将会存储在密匙签名中。您可以忽略某些或全部的输入项。";
    }

    @Override
    public String get_please_input_your_name() {
        return "您的名字";
    }

    @Override
    public String get_please_input_your_department() {
        return "您的部门";
    }

    @Override
    public String get_please_input_your_company() {
        return "您的公司";
    }

    @Override
    public String get_please_input_your_street_no() {
        return "您的街道地址";
    }

    @Override
    public String get_please_input_your_city() {
        return "您的城市";
    }

    @Override
    public String get_please_input_your_state() {
        return "您的省或州";
    }

    @Override
    public String get_please_input_your_state_code() {
        return "您的省或州代码";
    }

    @Override
    public String get_please_input_your_country() {
        return "您的国家或地区";
    }

    @Override
    public String get_please_input_your_contact_details() {
        return "请输入您的联系信息。这些信息将会出现在软件的帮助中。您可以忽略部分或全部输入项。";
    }

    @Override
    public String get_please_input_your_email() {
        return "您的电子邮件";
    }

    @Override
    public String get_please_input_your_website() {
        return "您的网址";
    }

    @Override
    public String get_test_key() {
        return "测试用密匙";
    }

    @Override
    public String get_new_key() {
        return "新密匙";
    }

    @Override
    public String get_cannot_delete_and_recreate_apk_tmp_folder() {
        return "无法删除然后重建用于创建APK包的临时目录。\n您可以手动删除apks/apk_generation_temp_folder_0041357目录然后再试一次。";
    }

    @Override
    public String get_cannot_create_apk_file() {
        return "无法创建apk文件！";
    }

    @Override
    public String get_do_you_want_to_replace_same_name_file() {
        return "您想替换同名文件吗？";
    }

    @Override
    public String get_cannot_replace_existing_keystore_file() {
        return "无法替换已经存在的储存密匙文件！";
    }

    @Override
    public String get_invalid_keystore_file() {
        return "非法的储存密匙文件！";
    }

    @Override
    public String get_invalid_keystore_password() {
        return "非法的储存密匙文件密码！";
    }

    @Override
    public String get_password_requirement() {
        return "密码必须包含最少8个字符，并且由数字和字母构成。";
    }

    @Override
    public String get_invalid_keystore_again_password() {
        return "两次储存密匙文件密码的输入不匹配！";
    }

    @Override
    public String get_invalid_key_name() {
        return "非法的密匙名！";
    }

    @Override
    public String get_invalid_key_valid_period() {
        return "密匙有效期限至少为30年！";
    }

    @Override
    public String get_invalid_key_password() {
        return "非法的密匙密码！";
    }

    @Override
    public String get_invalid_key_again_password() {
        return "两次密匙密码的输入不匹配！";
    }

    @Override
    public String get_cannot_create_key() {
        return "无法创建密匙！";
    }

    @Override
    public String get_done() {
        return "完成";
    }

    @Override
    public String get_apk_is_created() {
        return "Apk安装包已经创建。您可以在您的安卓设备上安装它，也可以发送给其他人安装。\n如果您是用一个公共密匙给这个安装包签名（公共密匙是您或他人创建的密匙），\n您可以在谷歌商店或者其他网站上发布您的应用。\nApk安装包的保存位子为";
    }

    @Override
    public String get_help() {
        return "帮助";
    }

    @Override
    public String get_brief_introduction() {
        return "本应用是由一个MFP函数生成，应用使用介绍、函数功能以及开发者联系方式见下";
    }

    @Override
    public String get_email_address() {
        return "电子邮件";
    }

    @Override
    public String get_web_site() {
        return "网站";
    }

    @Override
    public String get_developer_contact_details() {
        return "开发者联系方式";
    }

    @Override
    public String get_cannot_sign_apk() {
        return "无法给APK文件签名！";
    }

    @Override
    public String get_creating_and_signing_apk() {
        return "正在创建APK安装包...";
    }

    @Override
    public String get_install() {
        return "安装";
    }

    @Override
    public String get_share() {
        return "共享";
    }

    @Override
    public String get_back() {
        return "返回";
    }

    @Override
    public String get_step_1() {
        return "第一步";
    }

    @Override
    public String get_step_2() {
        return "第二步";
    }

    @Override
    public String get_step_3() {
        return "第三步";
    }

    @Override
    public String get_compilation_terminated_unexpectedly() {
        return "编译过程异常中止！";
    }

    @Override
    public String get_undefined_function() {
        return "没有定义的函数";
    }

    @Override
    public String get_line() {
        return "行";
    }

    @Override
    public String get_app_working_folder() {
        return "应用工作目录";
    }

    @Override
    public String get_your_sd_card() {
        return "您的SD卡";
    }

    @Override
    public String get_list_citingspace_info() {
        return "所有使用中的citingspaces排列如下，它们的优先级是从上到下。任意一个citingspace前面的！表示该citingspace不能被删除。";
    }

    @Override
    public String get_add_citingspace_succeeded() {
        return "Citingspace已经被加入。注意现在它有最高的优先级。";
    }

    @Override
    public String get_add_citingspace_failed() {
        return "Citingspace无法被加入。";
    }

    @Override
    public String get_delete_citingspace_succeeded() {
        return "Citingspace已经被删除。";
    }

    @Override
    public String get_delete_citingspace_failed() {
        return "Citingspace无法被删除。";
    }

    @Override
    public String get_invalid_shellman_command() {
        return "无效的shellman命令。";
    }

    @Override
    public String get_add_citingspace_succeeded2() {
        return "Citingspace已经被加入。注意它的优先级仅次于TOP Level Citingspace（也就是最上层的Citingspace）";
    }

    @Override
    public String get_shellman_command_need_cs_parameter() {
        return "Shellman命令需要citingspace作为参数。";
    }

    @Override
    public String get_1st_order_derivative() {
        return "一阶导数";
    }

    @Override
    public String get_2nd_order_derivative() {
        return "二阶导数";
    }

    @Override
    public String get_3rd_order_derivative() {
        return "三阶导数";
    }

    @Override
    public String get_derivative_expr_prompt() {
        return "待求导表达式";
    }

    @Override
    public String get_variable_name() {
        return "变量名";
    }

    @Override
    public String get_variable_value() {
        return "变量值";
    }

    @Override
    public String get_menu_calculus() {
        return "微积分";
    }

    @Override
    public String get_menu_derivative() {
        return "微分";
    }

    @Override
    public String get_derivative_input_title() {
        return "微分设置";
    }

    @Override
    public String get_cannot_find_settings_file_use_default() {
        return "无法找到设置文件，启用缺省设置";
    }

    @Override
    public String get_interrupt_running_task() {
        return "中断正在运行的任务";
    }

    @Override
    public String get_terminate_session() {
        return "程序终止";
    }

    @Override
    public String get_ctrl_c_not_supported() {
        return "由于操作系统不支持Ctrl-C，用户无法终止运行中的程序.";
    }

    @Override
    public String get_invalid_command_option() {
        return "非法命令选项。";
    }

    @Override
    public String get_no_script_file_name() {
        return "缺少脚本文件名。";
    }

    @Override
    public String get_invalid_lib_path() {
        return "非法库目录。";
    }

    @Override
    public String get_no_lib_path() {
        return "缺少库目录。";
    }

    @Override
    public String get_invalid_number_of_parameters() {
        return "参数数目不对。";
    }

    @Override
    public String get_invalid_entry_function() {
        return "非法的入口函数。也许您没有在脚本文件中正确地声明@execution_entry？";
    }

    @Override
    public String get_command_UI_help() {
        return "用法：\n-c (or /c)：命令提示符模式；\n-g (or /g)：基于用户图形界面的命令提示符模式；\n-L (or /L)：添加一个用户定义的mfps库目录。一条指令可以包含多个-L开关，每一个添加一个新的mfps库目录；\n-f (or /f)：运行一个脚本文件。-f开关后面的参数是脚本文件名。后面跟随着运行这个脚本所需要的参数。这个开关必须是本命令中的最后一个开关；\n-i (or /i)：打印出脚本的用法。-i开关后面的参数是脚本的文件名。这个开关必须是本命令中的最后一个开关；\n-v (or /v)：显示版本信息；\n-h (or /h)：打印出本帮助信息；";
    }

    @Override
    public String get_gui_not_supported() {
        return "用户图形界面不被支持。";
    }

    @Override
    public String get_not_a_folder() {
        return "不是文件夹";
    }

    @Override
    public String get_call() {
        return "调用";
    }

    @Override
    public String get_additional_user_defined_libs_prompt() {
        return "用户自定义的其他库（文件夹或者mfps文件，每一行为一个库）:";
    }

    @Override
    public String get_additional_usr_lib_folder_text() {
        return "选择目录";
    }

    @Override
    public String get_additional_usr_lib_mfps_text() {
        return "选择MFPS文件";
    }

    @Override
    public String get_select_file() {
        return "选择文件";
    }

    @Override
    public String get_variable_has_been_added() {
        return "变量已被加入。";
    }

    @Override
    public String get_shellman_command_need_parameter() {
        return "Shellman命令需要参数。";
    }

    @Override
    public String get_variable_has_been_deleted() {
        return "变量已被删除。";
    }

    @Override
    public String get_variable_not_exist() {
        return "变量不存在。";
    }

    @Override
    public String get_file() {
        return "文件";
    }

    @Override
    public String get_variable_not_exist_or_cannot_delete() {
        return "变量不存在或无法被删除。";
    }

    @Override
    public String get_do_you_want_to_exit() {
        return "您要退出吗？";
    }
}
