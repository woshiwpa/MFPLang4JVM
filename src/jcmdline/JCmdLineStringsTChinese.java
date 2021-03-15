/*
 * MFP project, JCmdLineStringsTChinese.java : Designed and developed by Tony Cui in 2021
 */
package jcmdline;

/**
 *
 * @author tonyc
 */
public class JCmdLineStringsTChinese extends JCmdLineStrings {

    @Override
    public String get_hello() {
        return "Hello World, ActivityMain!";
    }

    @Override
    public String get_app_name() {
        return "可編程科學計算器";
    }

    @Override
    public String get_alert_dialog_ok() {
        return "確定";
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
        return "繪制二維曲線";
    }

    @Override
    public String get_menu_integrate() {
        return "積分計算";
    }

    @Override
    public String get_menu_history() {
        return "歷史記錄";
    }

    @Override
    public String get_menu_settings() {
        return "設置";
    }

    @Override
    public String get_menu_help() {
        return "幫助";
    }

    @Override
    public String get_setting_number_format_prompt() {
        return "設置數值精度";
    }

    @Override
    public String get_setting_record_length_prompt() {
        return "設置記錄長度";
    }

    @Override
    public String get_setting_extreme_value_prompt() {
        return "設置科學計數";
    }

    @Override
    public String get_whats_new() {
        return "更新和增加的功能";
    }

    @Override
    public String get_do_not_show_whats_new_again() {
        return "下一次啟動時不再顯示。";
    }

    @Override
    public String get_welcome_message() {
        return "輸入help或表達式如：3 + log(4.1 / avg(1,5,-3))，然後按“計算”";
    }

    @Override
    public String get_EXPERROR_NO_EXPRESSION() {
        return "沒有表達式！";
    }

    @Override
    public String get_EXPERROR_MULTIPLE_EXPRESSIONS() {
        return "多個表達式！";
    }

    @Override
    public String get_EXPERROR_LACK_OPERATOR_BETWEEN_TWO_OPERANDS() {
        return "兩個計算數之間缺少計算符！";
    }

    @Override
    public String get_EXPERROR_NUM_CAN_NOT_HAVE_TWO_DECIMAL_POINT() {
        return "兩個小數點！";
    }

    @Override
    public String get_EXPERROR_NUM_DO_NOT_ACCORD_DECIMAL_NUM_WRITING_STANDARD() {
        return "數字格式不對！";
    }

    @Override
    public String get_EXPERROR_OPERATOR_NOT_EXIST() {
        return "不存在的操作符！";
    }

    @Override
    public String get_EXPERROR_UNMATCHED_RIGHTPARENTHESE() {
        return "右括號沒有對應的左括號！";
    }

    @Override
    public String get_EXPERROR_UNMATCHED_LEFTPARENTHESE() {
        return "左括號沒有對應的右括號！";
    }

    @Override
    public String get_EXPERROR_WRONG_OPERAND_TYPE() {
        return "不正確的操作數類型！";
    }

    @Override
    public String get_EXPERROR_INCORRECT_ANSWER_OF_POWER_FUNCTION_OPERANDS() {
        return "次方（power）函數的參數不正確！";
    }

    @Override
    public String get_EXPERROR_INCORRECT_BINARY_OPERATOR() {
        return "不正確的二元操作符！";
    }

    @Override
    public String get_EXPERROR_INCORRECT_MONADIC_OPERATOR() {
        return "不正確的一元操作符！";
    }

    @Override
    public String get_EXPERROR_LACK_OPERAND() {
        return "缺少操作數！";
    }

    @Override
    public String get_EXPERROR_CAN_NOT_IDENTIFIED_CHARACTER() {
        return "字符無法識別！";
    }

    @Override
    public String get_EXPERROR_DATATYPE_IS_NOT_DEFINED() {
        return "數據類型沒有定義！";
    }

    @Override
    public String get_EXPERROR_CAN_NOT_CHANGE_A_NOTEXIST_DATUM_TO_ANY_OTHER_DATATYPE() {
        return "無法將不存在的類型變成其它任何數據類型！";
    }

    @Override
    public String get_EXPERROR_NEW_DATATYPE_IS_NOT_DEFINED() {
        return "新數據類型沒有定義！";
    }

    @Override
    public String get_EXPERROR_INTEGER_OVERFLOW() {
        return "整數溢出！";
    }

    @Override
    public String get_EXPERROR_DOUBLE_OVERFLOW() {
        return "浮點數溢出！";
    }

    @Override
    public String get_EXPERROR_CHANGE_FROM_DOUBLE_TO_INTEGER_OVERFLOW() {
        return "將浮點數轉換為整數時溢出！";
    }

    @Override
    public String get_EXPERROR_OPERAND_OF_FACTORIAL_MAY_BE_TOO_LARGE() {
        return "階乘參數值過大！";
    }

    @Override
    public String get_EXPERROR_DIVISOR_CAN_NOT_BE_ZERO() {
        return "被零除！";
    }

    @Override
    public String get_EXPERROR_OPERAND_OF_FACTORIAL_CAN_NOT_LESS_THAN_ZERO() {
        return "負的階乘參數值！";
    }

    @Override
    public String get_EXPERROR_OPERAND_OF_BIT_OPERATION_MUST_BE_GREATER_THAN_ZERO() {
        return "位操作數必須為正數！";
    }

    @Override
    public String get_EXPERROR_INCORRECT_NUM_OF_PARAMETER() {
        return "參數數目不對！";
    }

    @Override
    public String get_EXPERROR_INVALID_PARAMETER_RANGE() {
        return "不正確的參數範圍！";
    }

    @Override
    public String get_EXPERROR_INVALID_PARAMETER_TYPE() {
        return "不正確的參數類型！";
    }

    @Override
    public String get_EXPERROR_UNDEFINED_FUNCTION() {
        return "沒有定義的函數！";
    }

    @Override
    public String get_EXPERROR_UNDEFINED_VARIABLE() {
        return "沒有定義的變量！";
    }

    @Override
    public String get_go_button() {
        return "計算!";
    }

    @Override
    public String get_numbers_operators_button_long() {
        return "數字\n操作符";
    }

    @Override
    public String get_common_functions_button_long() {
        return "通用函數";
    }

    @Override
    public String get_more_functions_button_long() {
        return "更多函數";
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
        return "刪除";
    }

    @Override
    public String get_help_button() {
        return "幫助";
    }

    @Override
    public String get_x_help_info() {
        return "X待解變數x的名字或者其他待解變數比如x1，xy，xx等的名字的一部分。";
    }

    @Override
    public String get_y_help_info() {
        return "Y待解變數y的名字或者其他待解變數比如y3，xy，yyyy等的名字的一部分。";
    }

    @Override
    public String get_z_help_info() {
        return "Z待解變數z的名字或者其他待解變數比如z2，zx，zzz等的名字的一部分。";
    }

    @Override
    public String get_assign_help_info() {
        return "賦值操作符。本操作符將一個數值賦給一個變量。比如，x = 3 + 4將數值7賦給變量x。";
    }

    @Override
    public String get_equal_help_info() {
        return "等於號。本符號意味著本符號左邊的表達式和符號右邊的表達式有同樣的數值。比如，x + 1 == 7 + 3意味著x + 1和7 + 3有同樣的數值，也就是10。通過這個表達式，我們可以解出x的值為9。";
    }

    @Override
    public String get_parenthesis_help_info() {
        return "左括號。位於一對括號中的表達式有更高的運算優先級。比如，x * (y + 3)。";
    }

    @Override
    public String get_closeparenthesis_help_info() {
        return "右括號。位於一對括號中的表達式有更高的運算優先級。比如，x * (y + 3)。";
    }

    @Override
    public String get_squarebracket_help_info() {
        return "左方括號。左方括號為一個數組的定義的開始。比如，[[1,2,3],[4,5,6]]定義了一個2*3矩陣，第一行為包括3個元素（也就是1，2和3）的數組，第二行也為包括3個元素（也就是4，5和6）的數組。該矩陣本身也為一個數組包括2個數組元素。";
    }

    @Override
    public String get_closesquarebracket_help_info() {
        return "右方括號。右方括號為一個數組的定義的結束。比如，[[1,2,3],[4,5,6]]定義了一個2*3矩陣，第一行為包括3個元素（也就是1，2和3）的數組，第二行也為包括3個元素（也就是4，5和6）的數組。該矩陣本身也為一個數組包括2個數組元素。";
    }

    @Override
    public String get_comma_help_info() {
        return "逗號將數組中的每一個元素分隔開，比如，數組[2,3,4,5]有4個元素被3個逗號分隔開。";
    }

    @Override
    public String get_plus_help_info() {
        return "正數或加號（加號支援復數，矩陣和字串）。";
    }

    @Override
    public String get_minus_help_info() {
        return "負數或減號（減號支援復數和矩陣）。";
    }

    @Override
    public String get_multiplication_help_info() {
        return "乘號（支援復數和矩陣）。";
    }

    @Override
    public String get_division_help_info() {
        return "除號（支援復數和矩陣）。";
    }

    @Override
    public String get_leftdivision_help_info() {
        return "左除號（主要用於矩陣相除，比如計算Ax=b中的x時，x=A\\b，註意第一個操作數是除數，第二個是被除數，如果除數不是矩陣，和除號的功能完全一樣）。";
    }

    @Override
    public String get_power_help_info() {
        return "次方。註意如果兩個運算數都可以為復數。";
    }

    @Override
    public String get_exclaimation_help_info() {
        return "如果位於操作數左邊是否操作符，如果位於操作數有變是階乘操作符。註意操作數必須是非負並且自動轉換為整數。";
    }

    @Override
    public String get_transpose_help_info() {
        return "二維矩陣轉秩。如果操作數是一維向量，返回一個二維矩陣，如果操作數不是矩陣，返回操作數本身。";
    }

    @Override
    public String get_doublequote_help_info() {
        return "雙引號，用於標示字串。";
    }

    @Override
    public String get_percentage_help_info() {
        return "百分號。例如，403.77% = 4.0377。";
    }

    @Override
    public String get_bit_and_help_info() {
        return "位與操作符，註意操作數必須為非負整數。";
    }

    @Override
    public String get_bit_or_help_info() {
        return "位或操作符，註意操作數必須為非負整數。";
    }

    @Override
    public String get_bit_xor_help_info() {
        return "位異或操作符，註意操作數必須為非負整數。";
    }

    @Override
    public String get_bit_not_help_info() {
        return "位否操作符，註意操作數必須為非負整數。";
    }

    @Override
    public String get_image_i_help_info() {
        return "用於復數計算的單位虛數值。";
    }

    @Override
    public String get_pi_constant_help_info() {
        return "圓周率。";
    }

    @Override
    public String get_e_constant_help_info() {
        return "自然對數e。註意e同時也用於科學計數，比如1.23e-002。";
    }

    @Override
    public String get_null_constant_help_info() {
        return "NULL（空值）。";
    }

    @Override
    public String get_true_constant_help_info() {
        return "布爾值true。";
    }

    @Override
    public String get_false_constant_help_info() {
        return "布爾值false。";
    }

    @Override
    public String get_inf_constant_help_info() {
        return "數值無窮大。";
    }

    @Override
    public String get_infi_constant_help_info() {
        return "虛數值無窮大。";
    }

    @Override
    public String get_nan_constant_help_info() {
        return "沒有定義的數值，比如0/0。";
    }

    @Override
    public String get_nani_constant_help_info() {
        return "沒有定義的虛數值。";
    }

    @Override
    public String get_no_quick_help_info() {
        return "沒有快速幫助信息對應於";
    }

    @Override
    public String get_calculator_settings() {
        return "計算器設置";
    }

    @Override
    public String get_digits_shown() {
        return "位數字";
    }

    @Override
    public String get_let_calculator_decide() {
        return "讓計算器決定";
    }

    @Override
    public String get_never_sci_notation() {
        return "不使用科學計數";
    }

    @Override
    public String get_always_sci_notation() {
        return "總是使用科學計數";
    }

    @Override
    public String get_if_log10_abs_result() {
        return "僅當log10(abs(結果)) >= ";
    }

    @Override
    public String get_records_shown() {
        return " 條記錄";
    }

    @Override
    public String get_enable_btn_press_vibration_prompt() {
        return "按計算器按鈕時震動";
    }

    @Override
    public String get_external_storage_mnt_folder_prompt() {
        return "存儲設備（比如SD卡）所在系統檔案目錄:";
    }

    @Override
    public String get_app_folder_prompt() {
        return "應用程式數據在存儲設備上的檔案目錄位於：";
    }

    @Override
    public String get_script_folder_prompt() {
        return "用戶定義的函數存儲於存儲設備檔案目錄：";
    }

    @Override
    public String get_chart_folder_prompt() {
        return "使用者生成的圖片存放於存儲設備檔案目錄：";
    }

    @Override
    public String get_settings_saved() {
        return "設置已經保存";
    }

    @Override
    public String get_select_record_title() {
        return "計算記錄";
    }

    @Override
    public String get_no_records() {
        return "沒有記錄！";
    }

    @Override
    public String get_select_input_type() {
        return "請選擇表達式或者結果以輸入";
    }

    @Override
    public String get_error_answer_shown() {
        return "錯誤";
    }

    @Override
    public String get_return_nothing_answer_shown() {
        return "沒有返回值";
    }

    @Override
    public String get_variables_declared_shown() {
        return "申明變量：";
    }

    @Override
    public String get_calculator_help() {
        return "計算器幫助";
    }

    @Override
    public String get_cmd_line_title() {
        return "命令行輸入";
    }

    @Override
    public String get_cmd_line_welcome_message() {
        return "請輸入表達式或者help加空格加函數名，然後按回車鍵。";
    }

    @Override
    public String get_command_prompt() {
        return "$>";
    }

    @Override
    public String get_last_cmd() {
        return "上一條指令";
    }

    @Override
    public String get_new_script() {
        return "新程式檔案";
    }

    @Override
    public String get_manage_scripts() {
        return "管理程式檔案";
    }

    @Override
    public String get_view_charts() {
        return "觀看圖形";
    }

    @Override
    public String get_error() {
        return "錯誤！";
    }

    @Override
    public String get_warning() {
        return "警告！";
    }

    @Override
    public String get_ok() {
        return "確定";
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
        return "關閉";
    }

    @Override
    public String get_script_file_manager_title() {
        return "程式檔案管理器";
    }

    @Override
    public String get_chart_file_manager_title() {
        return "圖形檔案管理器";
    }

    @Override
    public String get_file_manager_title() {
        return "檔案管理器";
    }

    @Override
    public String get_script_editor_title() {
        return "程式編輯器";
    }

    @Override
    public String get_script_file_saved() {
        return "檔案已經保存";
    }

    @Override
    public String get_file_folder_icon() {
        return "檔案或者檔案目錄圖標";
    }

    @Override
    public String get_file_manager_menu_new() {
        return "新建";
    }

    @Override
    public String get_file_manager_menu_open() {
        return "打開";
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
        return "刪除";
    }

    @Override
    public String get_file_manager_invalid_path() {
        return "非法路徑！";
    }

    @Override
    public String get_example_scripts_not_loaded() {
        return "example檔案目錄用於存放開發示例，該檔案目錄以及其子檔案目錄中的所有程式將不會被加載！";
    }

    @Override
    public String get_file_manager_new_file_type_script_file() {
        return "新的程式檔案";
    }

    @Override
    public String get_file_manager_new_file_type_folder() {
        return "新檔案目錄";
    }

    @Override
    public String get_file_manager_new_file_prompt() {
        return "請輸入檔案名：";
    }

    @Override
    public String get_file_manager_new_file_title() {
        return "創建新的檔案或檔案目錄";
    }

    @Override
    public String get_file_manager_new_file_failed_msg() {
        return "無法創建新檔案或檔案目錄！";
    }

    @Override
    public String get_file_manager_file_rename_title() {
        return "重命名";
    }

    @Override
    public String get_file_manager_file_new_name_prompt() {
        return "請輸入新的檔案名：";
    }

    @Override
    public String get_file_manager_file_rename_failed_msg() {
        return "無法重命名！";
    }

    @Override
    public String get_file_manager_file_delete_title() {
        return "刪除";
    }

    @Override
    public String get_file_manager_file_delete_confirm() {
        return "你確定要刪除";
    }

    @Override
    public String get_file_manager_file_delete_failed_msg() {
        return "無法刪除！";
    }

    @Override
    public String get_file_editor_file_open_fail() {
        return "無法打開檔案！";
    }

    @Override
    public String get_file_not_found() {
        return "找不到檔案！";
    }

    @Override
    public String get_file_editor_file_io_error() {
        return "IO錯誤！";
    }

    @Override
    public String get_file_editor_file_changed() {
        return "檔案已經被更改，是否保存？";
    }

    @Override
    public String get_file_editor_new_file() {
        return "新檔案";
    }

    @Override
    public String get_file_editor_file() {
        return "檔案";
    }

    @Override
    public String get_file_editor_inside() {
        return "位於";
    }

    @Override
    public String get_file_editor_menu_new() {
        return "新建";
    }

    @Override
    public String get_file_editor_menu_open() {
        return "打開";
    }

    @Override
    public String get_file_editor_menu_save() {
        return "保存";
    }

    @Override
    public String get_file_editor_menu_save_as() {
        return "另存為";
    }

    @Override
    public String get_file_editor_menu_goto_line() {
        return "跳轉至";
    }

    @Override
    public String get_file_editor_goto_line() {
        return "跳轉至行";
    }

    @Override
    public String get_plot_2d_title() {
        return "繪制2維曲線圖";
    }

    @Override
    public String get_graph_name_prompt() {
        return "圖像名：";
    }

    @Override
    public String get_graph_title_prompt() {
        return "圖像標題：";
    }

    @Override
    public String get_fill_surface() {
        return "是否填充表面";
    }

    @Override
    public String get_is_surface_grid() {
        return "不填充表面（僅繪制網格）";
    }

    @Override
    public String get_graph_Xtitle_prompt() {
        return "X軸標題：";
    }

    @Override
    public String get_graph_Ytitle_prompt() {
        return "Y軸標題：";
    }

    @Override
    public String get_graph_Ztitle_prompt() {
        return "Z軸標題：";
    }

    @Override
    public String get_graph_Rtitle_prompt() {
        return "R軸標題：";
    }

    @Override
    public String get_graph_show_grid_chkbox_prompt() {
        return "顯示\n網格";
    }

    @Override
    public String get_add_curve_btn_text() {
        return "添加曲線";
    }

    @Override
    public String get_clear_all_btn_text() {
        return "清除所有";
    }

    @Override
    public String get_generate_chart_btn_text() {
        return "觀看";
    }

    @Override
    public String get_curve_title_prompt() {
        return "標題：";
    }

    @Override
    public String get_curve_color_prompt() {
        return "顏色：";
    }

    @Override
    public String get_curve_point_style_prompt() {
        return "點的形狀：";
    }

    @Override
    public String get_curve_show_line_chkbox_prompt() {
        return "顯示連接線";
    }

    @Override
    public String get_max_color_prompt() {
        return "最大值所對應的顏色（正反面）：";
    }

    @Override
    public String get_max_color_value_prompt() {
        return "最大值為：";
    }

    @Override
    public String get_min_color_prompt() {
        return "最小值所對應的顏色（正反面）：";
    }

    @Override
    public String get_min_color_value_prompt() {
        return "最小值為：";
    }

    @Override
    public String get_t_from_prompt() {
        return "t：從";
    }

    @Override
    public String get_t_to_prompt() {
        return "到";
    }

    @Override
    public String get_t_step_prompt() {
        return "間隔";
    }

    @Override
    public String get_u_from_prompt() {
        return "u：從";
    }

    @Override
    public String get_u_to_prompt() {
        return "到";
    }

    @Override
    public String get_u_step_prompt() {
        return "間隔";
    }

    @Override
    public String get_v_from_prompt() {
        return "v：從";
    }

    @Override
    public String get_v_to_prompt() {
        return "到";
    }

    @Override
    public String get_v_step_prompt() {
        return "間隔";
    }

    @Override
    public String get_delete_curve_btn_text() {
        return "刪除曲線";
    }

    @Override
    public String get_clear_curve_btn_text() {
        return "清除曲線定義";
    }

    @Override
    public String get_circle_point_style() {
        return "圓形";
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
        return "對角叉";
    }

    @Override
    public String get_point_point_style() {
        return "點";
    }

    @Override
    public String get_black_color() {
        return "黑色";
    }

    @Override
    public String get_blue_color() {
        return "藍色";
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
        return "綠色";
    }

    @Override
    public String get_ltgray_color() {
        return "淺灰色";
    }

    @Override
    public String get_magenta_color() {
        return "紫紅色";
    }

    @Override
    public String get_orange_color() {
        return "橘黃色";
    }

    @Override
    public String get_pink_color() {
        return "粉紅色";
    }

    @Override
    public String get_red_color() {
        return "紅色";
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
        return "黃色";
    }

    @Override
    public String get_graph_settings_wrong() {
        return "圖像設置有問題所以無法繪制！";
    }

    @Override
    public String get_graph_file_cannot_be_read() {
        return "在閱讀圖像檔案時發生錯誤！";
    }

    @Override
    public String get_graph_file_cannot_be_saved() {
        return "在保存圖像檔案時發生錯誤！";
    }

    @Override
    public String get_please_wait() {
        return "請等待";
    }

    @Override
    public String get_calculating_chart_data() {
        return "正在進行繪制圖形所需要的計算...";
    }

    @Override
    public String get_integrate_title() {
        return "積分";
    }

    @Override
    public String get_integrated_expr_prompt() {
        return "被積分\n表達式：";
    }

    @Override
    public String get_you_want_to_calculate() {
        return "您想要計算：";
    }

    @Override
    public String get_x_variable_name() {
        return "第一個積分變量的名字：";
    }

    @Override
    public String get_y_variable_name() {
        return "第二個積分變量的名字：";
    }

    @Override
    public String get_z_variable_name() {
        return "第三個積分變量的名字：";
    }

    @Override
    public String get_variable_from() {
        return "從";
    }

    @Override
    public String get_variable_to() {
        return "到";
    }

    @Override
    public String get_variable_number_of_steps() {
        return "積分步數";
    }

    @Override
    public String get_integ_settings_wrong() {
        return "積分輸入有問題，積分無法計算！";
    }

    @Override
    public String get_calculating_integrating_result() {
        return "正在積分積分結果...";
    }

    @Override
    public String get_integrating_result() {
        return "積分結果";
    }

    @Override
    public String get_cannot_create_app_folder() {
        return "無法生成數據檔案目錄，使用者將無法保存自己開發的函數和瀏覽生成的圖片！";
    }

    @Override
    public String get_cannot_create_script_folder() {
        return "無法生成函數程式檔案目錄，使用者將無法保存自己開發的函數！";
    }

    @Override
    public String get_cannot_create_chart_folder() {
        return "無法生成圖片檔案目錄，使用者將無法瀏覽生成的圖片！";
    }

    @Override
    public String get_function_name() {
        return "函數名";
    }

    @Override
    public String get_function_info() {
        return "函數幫助信息";
    }

    @Override
    public String get_all_functions() {
        return "所有函數";
    }

    @Override
    public String get_builtin_functions() {
        return "內置函數";
    }

    @Override
    public String get_predefined_functions() {
        return "預定義函數";
    }

    @Override
    public String get_integer_operation_functions() {
        return "整數操作函數";
    }

    @Override
    public String get_logic_functions() {
        return "邏輯函數";
    }

    @Override
    public String get_statistic_and_stochastic_functions() {
        return "統計和隨機函數";
    }

    @Override
    public String get_trigononmetric_functions() {
        return "三角函數";
    }

    @Override
    public String get_exponential_and_logarithmic_functions() {
        return "指數對數和次方函數";
    }

    @Override
    public String get_complex_number_functions() {
        return "復數操作函數";
    }

    @Override
    public String get_system_functions() {
        return "系統函數";
    }

    @Override
    public String get_array_or_matrix_functions() {
        return "數組和矩陣函數";
    }

    @Override
    public String get_graphic_functions() {
        return "圖形函數";
    }

    @Override
    public String get_expression_and_integration_functions() {
        return "表達式和積分函數";
    }

    @Override
    public String get_string_functions() {
        return "字符串函數";
    }

    @Override
    public String get_hyperbolic_trigononmetric_functions() {
        return "雙曲三角函數";
    }

    @Override
    public String get_sorting_functions() {
        return "排序函數";
    }

    @Override
    public String get_others_functions() {
        return "其他函數";
    }

    @Override
    public String get_exception() {
        return "異常！";
    }

    @Override
    public String get_back_to_normal() {
        return "回復正常。";
    }

    @Override
    public String get_unmounted_external_storage() {
        return "無法找到外接存儲器（比如SD卡）！";
    }

    @Override
    public String get_mounting_external_storage() {
        return "正在尋找并裝載外接存儲器。注意在此過程中計算可能會出現中斷。";
    }

    @Override
    public String get_mounted_external_storage() {
        return "找到并已裝載外接存儲器。";
    }

    // added for Java program
    @Override
    public String get_wrong_file_type() {
        return "錯誤的文檔類型！";
    }

    @Override
    public String get_cannot_open_file() {
        return "無法打開文檔！";
    }

    @Override
    public String get_cannot_find_file() {
        return "無法找到文檔！";
    }

    @Override
    public String get_error_in_analyzing_help_requirement() {
        return "分析幫助命令時出現錯誤！";
    }

    @Override
    public String get_select_folder() {
        return "選擇文檔夾";
    }

    @Override
    public String get_copy_to_main_window() {
        return "拷貝到主窗口";
    }

    @Override
    public String get_app_quick_introduction() {
        return "本軟體是一個類似Matlab的，進行數學分析的有力工具。";
    }

    @Override
    public String get_product_version() {
        return "版本：";
    }

    @Override
    public String get_product_vendor() {
        return "開發商：";
    }

    @Override
    public String get_product_homepage() {
        return "主頁：";
    }

    @Override
    public String get_menu_file() {
        return "文檔";
    }

    @Override
    public String get_menu_reload_lib() {
        return "刷新程序庫";
    }

    @Override
    public String get_menu_edit() {
        return "編輯";
    }

    @Override
    public String get_menu_interrupt_cmd() {
        return "終止運行中的任務";
    }

    @Override
    public String get_menu_wrap_line() {
        return "自動換行";
    }

    @Override
    public String get_menu_select_all() {
        return "全選";
    }

    @Override
    public String get_menu_copy() {
        return "拷貝";
    }

    @Override
    public String get_menu_paste() {
        return "粘貼";
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
        return "獲取常數值";
    }

    @Override
    public String get_menu_convert_unit() {
        return "進行單位轉換";
    }

    @Override
    public String get_menu_calc_polynomial_roots() {
        return "計算多項式的根";
    }

    @Override
    public String get_menu_plot_multixy_graph() {
        return "繪制二維圖形";
    }

    @Override
    public String get_menu_plot_polar_graph() {
        return "繪制極坐標圖形";
    }

    @Override
    public String get_menu_plot_multixyz_graph() {
        return "繪制三維圖形";
    }

    @Override
    public String get_menu_view_chart() {
        return "打開圖像文檔";
    }

    @Override
    public String get_menu_content() {
        return "內容";
    }

    @Override
    public String get_menu_about() {
        return "關於...";
    }

    @Override
    public String get_about() {
        return "關於";
    }

    @Override
    public String get_integration_input_title() {
        return "積分設置";
    }

    @Override
    public String get_XY_chart_def_title() {
        return "繪制二維圖形";
    }

    @Override
    public String get_Polar_chart_def_title() {
        return "繪制極坐標圖形";
    }

    @Override
    public String get_XYZ_chart_def_title() {
        return "繪制三維圖形";
    }

    @Override
    public String get_settings_dialog_title() {
        return "設置";
    }

    @Override
    public String get_select_user_defined_functions_folder_prompt() {
        return "選擇用於存放用戶自定義函數的文檔夾：";
    }

    @Override
    public String get_select_charts_folder_prompt() {
        return "選擇用於存放圖像文檔的文檔夾：";
    }

    @Override
    public String get_select_btn_text() {
        return "選擇";
    }

    @Override
    public String get_setting_scientific_notation_thresh_prompt() {
        return "使用科學計數法顯示計算結果如果結果大於：";
    }

    @Override
    public String get_single_line_integrated_expr_prompt() {
        return "被積分表達式：";
    }

    @Override
    public String get_indefinite_integral() {
        return "不定積分";
    }

    @Override
    public String get_single_integral() {
        return "一次積分";
    }

    @Override
    public String get_double_integral() {
        return "二次積分";
    }

    @Override
    public String get_triple_integral() {
        return "三次積分";
    }

    @Override
    public String get_wait_for_calculation() {
        return "計算任務正在運行，待它執行完成之後，";
    }

    @Override
    public String get_help_get_constant_info() {
        return "請您運行\"help get_constant\"以獲取更多信息！";
    }

    @Override
    public String get_help_convert_unit_info() {
        return "請您運行\"help convert_unit\"以獲取更多信息！";
    }

    @Override
    public String get_help_calc_polynomial_roots_info() {
        return "請您運行\"help roots\"以獲取更多信息！";
    }

    @Override
    public String get_interrupt_running_cmd() {
        return "Ctrl-D被按下，運行中的任務被終止！";
    }

    @Override
    public String get_session_starts() {
        return "命令集開始執行";
    }

    @Override
    public String get_session_returns() {
        return "命令集返回";
    }

    @Override
    public String get_cannot_find_asset_files() {
        return "Asset檔案目錄缺失。您可能無法使用一些函數程式或者幫助文檔。";
    }

    @Override
    public String get_find_corrupted_asset_files() {
        return "無法生成一些支援檔案。您可能無法使用一些函數程式或者幫助文檔。";
    }

    @Override
    public String get_starting() {
        return "正在啟動...";
    }

    @Override
    public String get_unzipping_asset_files() {
        return "正在解壓縮支援檔案...";
    }

    @Override
    public String get_loading_functions() {
        return "正在裝載程式庫...";
    }

    @Override
    public String get_receive_following_errors() {
        return "在啟動過程中收到如下錯誤資訊：";
    }

    @Override
    public String get_cannot_load_opengl_libs() {
        return "Open GL庫無法裝載，這將造成三維圖像無法繪制！";
    }

    @Override
    public String get_ogl_chart_cannot_plot_lack_system_libs() {
        return "由於缺少和系統平臺相關的庫文件造成三維圖像無法繪制，請和開發人員聯系以獲取您的系統平臺的庫文件。";
    }

    @Override
    public String get_multiXYZ_config() {
        return "設置3D圖形";
    }

    @Override
    public String get_multiXYZ_settings_zoom_prompt() {
        return "全圖縮放";
    }

    @Override
    public String get_multiXYZ_settings_x_zoom_prompt() {
        return "x軸縮放";
    }

    @Override
    public String get_multiXYZ_settings_y_zoom_prompt() {
        return "y軸縮放";
    }

    @Override
    public String get_multiXYZ_settings_z_zoom_prompt() {
        return "z軸縮放";
    }

    @Override
    public String get_multiXYZ_settings_x_shift_prompt() {
        return "沿x軸向平移";
    }

    @Override
    public String get_multiXYZ_settings_y_shift_prompt() {
        return "沿y軸向平移";
    }

    @Override
    public String get_multiXYZ_settings_z_shift_prompt() {
        return "沿z軸向平移";
    }

    @Override
    public String get_multiXYZ_settings_x_rotate_prompt() {
        return "繞水平軸轉動（角度）";
    }

    @Override
    public String get_multiXYZ_settings_y_rotate_prompt() {
        return "繞垂直軸轉動（角度）";
    }

    @Override
    public String get_multiXYZ_settings_z_rotate_prompt() {
        return "逆時針轉動（角度）";
    }

    @Override
    public String get_input_invalid_number() {
        return "請輸入一個數！";
    }

    @Override
    public String get_input_number_invalid_range() {
        return "輸入的數不是在合法的範圍，它不應該";
    }

    @Override
    public String get_apply() {
        return "應用";
    }

    @Override
    public String get_3DExpr_config() {
        return "設置基於表達式的3D圖形";
    }

    @Override
    public String get_XYExpr_config() {
        return "設置基於表達式的2D圖形";
    }

    @Override
    public String get_PolarExpr_config() {
        return "設置基於表達式的極坐標圖形";
    }

    @Override
    public String get_detect_singular_points() {
        return "偵測奇異點";
    }

    @Override
    public String get_app_in_a_read_only_folder() {
        return "請您將整個AnMath檔案目錄拷貝至一個可讀寫的位子然後再運行基於JAVA的程式開發科學計算器。";
    }

    @Override
    public String get_miss_asset_or_app_in_a_zipped_folder_or_unmapped_usb() {
        return "请确定没有文件缺失（比如缺少assets.zip）并拷贝整个AnMath目录至一个可读写的位子然后再\n运行本软件。";
    }

    @Override
    public String get_app_in_a_read_only_folder_full() {
        return "基於JAVA的程式開發科學計算器可能位於一個只讀檔案目錄中。請您將整個AnMath\n檔案目錄拷貝至一個可讀寫的位子然後再運行基於JAVA的程式開發科學計算器。";
    }

    @Override
    public String get_miss_asset_or_app_in_a_zipped_folder_or_unmapped_usb_full() {
        return "基于JAVA的可编程科学计算器可能缺少一些文件（比如assets.zip）或者可能位于一个\n压缩文件夹中或者移动设备的USB驱动程序不支持从移动设备的存储器中运行可执行文件。\n请确定没有文件缺失并拷贝整个AnMath目录至一个可读写的位子然后再运行本软件。";
    }

    @Override
    public String get_plot_chart_variable_range_prompt() {
        return "用plot_exprs等函數程式繪制圖形時的變數範圍：";
    }

    @Override
    public String get_plot_chart_variable_range_to_prompt() {
        return "到";
    }

    @Override
    public String get_Polar_chart_r_range_note() {
        return "註意：極坐標半徑範圍總是從0到一個正數值。";
    }

    @Override
    public String get_Polar_chart_angle_range_note() {
        return "註意：極坐標幅角是基於";
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
        return "不顯示坐標軸和標題";
    }

    @Override
    public String get_not_show_axis() {
        return "不顯示坐標軸";
    }

    @Override
    public String get_not_show_title() {
        return "不顯示標題";
    }

    @Override
    public String get_application_name_prompt() {
        return "請輸入應用名。應用名長度不超過32字節。這意味著32個英文字母或者最多（有可能少於）16個中文漢字。";
    }

    @Override
    public String get_application_pkg_id_prompt() {
        return "請輸入應用包ID。ID必須正好20個字符長。ID必須是唯一的，否則您無法在谷歌商店或者其他任何應用發布站點發布您的應用。ID只能包含英文字母，數字和點。點不能是第一個或者最後一個字符，也不能有兩個點相連。數字不能為第一個字符，也不能緊跟在點的後面。兩點之間不能有多於10個字母或者數字。例如：com.cyzapps.AnMFPApp 。";
    }

    @Override
    public String get_application_version_prompt() {
        return "請輸入您的應用的版本代碼（左邊的輸入）和版本號（右邊的輸入）。應用的版本代碼是一個包含10個英文字符或者數字或者點的字串，比如1.0.0.0001；應用的版本號則是一個正整數不大於65535。";
    }

    @Override
    public String get_application_icon_selector_prompt() {
        return "請選擇您的應用圖標。應用的圖標文件必須是一個正方形，不小於128乘以128的png文件。如果您不做選擇，默認的圖標將會被自動地賦予您的應用。選中的圖標將會被顯示在選擇按鈕上。";
    }

    @Override
    public String get_application_working_folder_prompt() {
        return "請輸入您的應用在SD卡上的工作檔案目錄";
    }

    @Override
    public String get_application_working_folder_hint() {
        return "如果您沒有輸入，缺省工作檔案目錄將會是包ID的最後一部分。比如包ID為com.cyzapps.MFPApp，則默認的工作檔案目錄為SD卡上的MFPApp目錄。";
    }

    @Override
    public String get_selected_icon_invalid() {
        return "應用的圖標文件必須是一個正方形，不小於128乘以128的png文件。";
    }

    @Override
    public String get_select() {
        return "選擇";
    }

    @Override
    public String get_application_description_prompt() {
        return "請簡要描述您的應用，比如，您的應用做些什麽，需要用戶輸入什麽。註意這不是用戶手冊，所以必須簡短（不多於256個字符）。它將在您的應用啟動時顯示。您也可以忽略此項輸入。";
    }

    @Override
    public String get_go_to_next() {
        return "繼續";
    }

    @Override
    public String get_mfpapp_prog_name() {
        return "建立MFP應用";
    }

    @Override
    public String get_long_click_to_open() {
        return "長按打開檔案夾或者檔案";
    }

    @Override
    public String get_app_name_invalid() {
        return "應用名不符合要求！";
    }

    @Override
    public String get_app_pkg_id_invalid() {
        return "應用包ID不符合要求！";
    }

    @Override
    public String get_app_ver_str_invalid() {
        return "應用版本字串不符合要求！";
    }

    @Override
    public String get_app_ver_code_invalid() {
        return "應用版本號不符合要求！";
    }

    @Override
    public String get_app_working_folder_invalid() {
        return "應用工作檔案目錄名稱非法！";
    }

    @Override
    public String get_help_use_default_app_description_or_type() {
        return "選擇自動生成應用描述作為當用戶點擊幫助菜單時的幫助頁面。自動生成的幫助頁面將包含函數程式幫助以及您的email和主頁。如果您不選擇自動生成應用描述，您可以將自定義的應用幫助寫在下面的文本框中：";
    }

    @Override
    public String get_function_name_prompt() {
        return "函數程式名";
    }

    @Override
    public String get_function_name_invalid() {
        return "非法程式名！";
    }

    @Override
    public String get_function_description_prompt() {
        return "請簡要描述您的函數程式，比如，您的函數程式做些什麽，需要用戶輸入什麽。註意這不是用戶手冊，所以必須簡短（不多於1024個字符）。它將在您的應用啟動時顯示。您也可以忽略此項輸入。";
    }

    @Override
    public String get_function_description_invalid() {
        return "函數程式描述過長！";
    }

    @Override
    public String get_function_help() {
        return "函數程式幫助";
    }

    @Override
    public String get_information_about_parameter() {
        return "關於該參數的信息";
    }

    @Override
    public String get_parameter_default_value() {
        return "該參數的默認值";
    }

    @Override
    public String get_with_optional_params() {
        return "使用可選參數";
    }

    @Override
    public String get_param_is_a_string() {
        return "參數是一個字符串";
    }

    @Override
    public String get_param_needs_no_input() {
        return "參數無需用戶輸入";
    }

    @Override
    public String get_add() {
        return "添加";
    }

    @Override
    public String get_delete() {
        return "刪除";
    }

    @Override
    public String get_delete_all() {
        return "刪除所有";
    }

    @Override
    public String get_please_input_apk_name() {
        return "請輸入您的apk檔案名。Apk檔案將會在您的AnMath\\apks檔案目錄中生成。";
    }

    @Override
    public String get_select_apk_signiture_key() {
        return "選擇密匙：";
    }

    @Override
    public String get_please_input_keystore_name() {
        return "請輸入儲存密匙檔案名。該檔案將會在於您的AnMath\\signkeys檔案目錄中生成。";
    }

    @Override
    public String get_please_input_keystore_password() {
        return "儲存密匙檔案密碼";
    }

    @Override
    public String get_please_input_password_again() {
        return "再次輸入密碼";
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
        return "密匙密碼";
    }

    @Override
    public String get_please_input_your_personal_information() {
        return "請輸入您的個人或公司信息。這些信息將會存儲在密匙簽名中。您可以忽略某些或全部的輸入項。";
    }

    @Override
    public String get_please_input_your_name() {
        return "您的名字";
    }

    @Override
    public String get_please_input_your_department() {
        return "您的部門";
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
        return "您的省或州代碼";
    }

    @Override
    public String get_please_input_your_country() {
        return "您的國家或地區";
    }

    @Override
    public String get_please_input_your_contact_details() {
        return "請輸入您的聯系信息。這些信息將會出現在軟體的幫助中。您可以忽略部分或全部輸入項。";
    }

    @Override
    public String get_please_input_your_email() {
        return "您的電子郵件";
    }

    @Override
    public String get_please_input_your_website() {
        return "您的網址";
    }

    @Override
    public String get_test_key() {
        return "測試用密匙";
    }

    @Override
    public String get_new_key() {
        return "新密匙";
    }

    @Override
    public String get_cannot_delete_and_recreate_apk_tmp_folder() {
        return "無法刪除然後重建用於創建APK包的臨時檔案目錄。\n您可以手動刪除apks/apk_generation_temp_folder_0041357檔案目錄然後再試一次。";
    }

    @Override
    public String get_cannot_create_apk_file() {
        return "無法創建apk檔案！";
    }

    @Override
    public String get_do_you_want_to_replace_same_name_file() {
        return "您想替換同名檔案嗎？";
    }

    @Override
    public String get_cannot_replace_existing_keystore_file() {
        return "無法替換已經存在的儲存密匙檔案！";
    }

    @Override
    public String get_invalid_keystore_file() {
        return "非法的儲存密匙檔案！";
    }

    @Override
    public String get_invalid_keystore_password() {
        return "非法的儲存密匙檔案密碼！";
    }

    @Override
    public String get_password_requirement() {
        return "密碼必須包含最少8個字符，並且由數字和字母構成。";
    }

    @Override
    public String get_invalid_keystore_again_password() {
        return "兩次儲存密匙檔案密碼的輸入不匹配！";
    }

    @Override
    public String get_invalid_key_name() {
        return "非法的密匙名！";
    }

    @Override
    public String get_invalid_key_valid_period() {
        return "密匙有效期限至少為30年！";
    }

    @Override
    public String get_invalid_key_password() {
        return "非法的密匙密碼！";
    }

    @Override
    public String get_invalid_key_again_password() {
        return "兩次密匙密碼的輸入不匹配！";
    }

    @Override
    public String get_cannot_create_key() {
        return "無法創建密匙！";
    }

    @Override
    public String get_done() {
        return "完成";
    }

    @Override
    public String get_apk_is_created() {
        return "Apk安裝包已經創建。您可以在您的安卓設備上安裝它，也可以發送給其他人安裝。\n如果您是用一個公共密匙給這個安裝包簽名（公共密匙是您或他人創建的密匙），\n您可以在谷歌商店或者其他網站上發布您的應用。\nApk安裝包的保存位子為";
    }

    @Override
    public String get_help() {
        return "幫助";
    }

    @Override
    public String get_brief_introduction() {
        return "本應用是由一個MFP函數程式生成，應用使用介紹、程式功能以及開發者聯系方式見下";
    }

    @Override
    public String get_email_address() {
        return "電子郵件";
    }

    @Override
    public String get_web_site() {
        return "網站";
    }

    @Override
    public String get_developer_contact_details() {
        return "開發者聯系方式";
    }

    @Override
    public String get_cannot_sign_apk() {
        return "無法給APK檔案簽名！";
    }

    @Override
    public String get_creating_and_signing_apk() {
        return "正在創建APK安裝包...";
    }

    @Override
    public String get_install() {
        return "安裝";
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
        return "編譯過程異常中止！";
    }

    @Override
    public String get_undefined_function() {
        return "未定義的程式";
    }

    @Override
    public String get_line() {
        return "行";
    }

    @Override
    public String get_app_working_folder() {
        return "應用工作檔案目錄";
    }

    @Override
    public String get_your_sd_card() {
        return "您的SD卡";
    }

    @Override
    public String get_list_citingspace_info() {
        return "所有使用中的citingspaces排列如下，它們的優先級是從上到下。任意壹個citingspace前面的！表示該citingspace不能被刪除。";
    }

    @Override
    public String get_add_citingspace_succeeded() {
        return "Citingspace已經被加入。註意現在它有最高的優先級。";
    }

    @Override
    public String get_add_citingspace_failed() {
        return "Citingspace無法被加入。";
    }

    @Override
    public String get_delete_citingspace_succeeded() {
        return "Citingspace已經被刪除。";
    }

    @Override
    public String get_delete_citingspace_failed() {
        return "Citingspace無法被刪除。";
    }

    @Override
    public String get_invalid_shellman_command() {
        return "無效的shellman命令。";
    }

    @Override
    public String get_add_citingspace_succeeded2() {
        return "Citingspace已經被加入。註意它的優先級僅次於TOP Level Citingspace（也就是最上層的Citingspace）";
    }

    @Override
    public String get_shellman_command_need_cs_parameter() {
        return "Shellman命令需要citingspace作為參數。";
    }

    @Override
    public String get_1st_order_derivative() {
        return "一階導數";
    }

    @Override
    public String get_2nd_order_derivative() {
        return "二階導數";
    }

    @Override
    public String get_3rd_order_derivative() {
        return "三階導數";
    }

    @Override
    public String get_derivative_expr_prompt() {
        return "待求導表達式";
    }

    @Override
    public String get_variable_name() {
        return "變量名";
    }

    @Override
    public String get_variable_value() {
        return "變量值";
    }

    @Override
    public String get_menu_calculus() {
        return "微積分";
    }

    @Override
    public String get_menu_derivative() {
        return "微分";
    }

    @Override
    public String get_derivative_input_title() {
        return "微分設置";
    }

    @Override
    public String get_cannot_find_settings_file_use_default() {
        return "無法找到設置檔案，啟用缺省設置";
    }

    @Override
    public String get_interrupt_running_task() {
        return "中斷正在運行的任務";
    }

    @Override
    public String get_terminate_session() {
        return "程式終止";
    }

    @Override
    public String get_ctrl_c_not_supported() {
        return "由於操作系統不支持Ctrl-C，用戶無法終止運行中的程序.";
    }

    @Override
    public String get_invalid_command_option() {
        return "非法命令選項。";
    }

    @Override
    public String get_no_script_file_name() {
        return "缺少腳本檔案名。";
    }

    @Override
    public String get_invalid_lib_path() {
        return "非法庫檔案目錄。";
    }

    @Override
    public String get_no_lib_path() {
        return "缺少庫檔案目錄。";
    }

    @Override
    public String get_invalid_number_of_parameters() {
        return "變數數目不對。";
    }

    @Override
    public String get_invalid_entry_function() {
        return "非法的入口函數程式。也許您沒有在腳本檔案中正確地聲明@execution_entry？";
    }

    @Override
    public String get_command_UI_help() {
        return "用法：\n-c (or /c)：命令提示符模式；\n-g (or /g)：基於用戶圖形界面的命令提示符模式；\n-L (or /L)：添加一個用戶定義的mfps庫檔案目錄。一條指令可以包含多個-L開關，每一個添加一個新的mfps庫檔案目錄；\n-f (or /f)：運行一個腳本檔案。-f開關後面的變數是腳本檔案名。後面跟隨著運行這個腳本所需要的變數。這個開關必須是本命令中的最後一個開關；\n-i (or /i)：打印出腳本的用法。-i開關後面的變數是腳本的檔案名。這個開關必須是本命令中的最後一個開關；\n-v (or /v)：顯示版本信息；\n-h (or /h)：打印出本幫助信息；";
    }

    @Override
    public String get_gui_not_supported() {
        return "用戶圖形界面不被支持。";
    }

    @Override
    public String get_not_a_folder() {
        return "不是檔案目錄";
    }

    @Override
    public String get_call() {
        return "調用";
    }

    @Override
    public String get_additional_user_defined_libs_prompt() {
        return "用戶自定義的其他庫（檔案目錄或者mfps檔案，每一行為一個庫）:";
    }

    @Override
    public String get_additional_usr_lib_folder_text() {
        return "選擇檔案目錄";
    }

    @Override
    public String get_additional_usr_lib_mfps_text() {
        return "選擇MFPS檔案";
    }

    @Override
    public String get_select_file() {
        return "選擇檔案";
    }

    @Override
    public String get_variable_has_been_added() {
        return "變數已被加入。";
    }

    @Override
    public String get_shellman_command_need_parameter() {
        return "Shellman命令需要參數。";
    }

    @Override
    public String get_variable_has_been_deleted() {
        return "變數已被刪除。";
    }

    @Override
    public String get_variable_not_exist() {
        return "變數不存在。";
    }

    @Override
    public String get_file() {
        return "檔案";
    }

    @Override
    public String get_variable_not_exist_or_cannot_delete() {
        return "變數不存在或無法被刪除。";
    }

    @Override
    public String get_do_you_want_to_exit() {
        return "您要退出嗎？";
    }
}
