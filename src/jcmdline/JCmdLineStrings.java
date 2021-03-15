/*
 * MFP project, JCmdLineStrings.java : Designed and developed by Tony Cui in 2021
 */
package jcmdline;

/**
 * The multi-language support class
 */
public class JCmdLineStrings {

    public String get_hello() {
        return "Hello World, ActivityMain!";
    }

    public String get_app_name() {
        return "Scientific Calculator Plus for Java";
    }

    public String get_alert_dialog_ok() {
        return "OK";
    }

    public String get_alert_dialog_cancel() {
        return "Cancel";
    }

    public String get_menu_cmdline() {
        return "Command line";
    }

    public String get_menu_plotgraph() {
        return "Plot 2D chart";
    }

    public String get_menu_integrate() {
        return "Integrate";
    }

    public String get_menu_history() {
        return "History";
    }

    public String get_menu_settings() {
        return "Settings";
    }

    public String get_menu_help() {
        return "Help";
    }

    public String get_setting_number_format_prompt() {
        return "Set bits of precision:";
    }

    public String get_setting_record_length_prompt() {
        return "Set record length:";
    }

    public String get_setting_extreme_value_prompt() {
        return "Set Scientific Notation";
    }

    public String get_whats_new() {
        return "What\'s new";
    }

    public String get_do_not_show_whats_new_again() {
        return "Do not show this box next time.";
    }

    public String get_welcome_message() {
        return "Input help or an expression like 3 + log(4.1 / avg(1,5,-3)), then press Go!";
    }

    public String get_EXPERROR_NO_EXPRESSION() {
        return "No expression!";
    }

    public String get_EXPERROR_MULTIPLE_EXPRESSIONS() {
        return "Multiple expressions!";
    }

    public String get_EXPERROR_LACK_OPERATOR_BETWEEN_TWO_OPERANDS() {
        return "Lack of operator between two operands!";
    }

    public String get_EXPERROR_NUM_CAN_NOT_HAVE_TWO_DECIMAL_POINT() {
        return "Two decimal points in one number!";
    }

    public String get_EXPERROR_NUM_DO_NOT_ACCORD_DECIMAL_NUM_WRITING_STANDARD() {
        return "Does not match with decimal number writing standard!";
    }

    public String get_EXPERROR_OPERATOR_NOT_EXIST() {
        return "No-exist operator!";
    }

    public String get_EXPERROR_UNMATCHED_RIGHTPARENTHESE() {
        return "Cannot find a left parnthese to match the right parenthese!";
    }

    public String get_EXPERROR_UNMATCHED_LEFTPARENTHESE() {
        return "Cannot find a right parnthese to match the left parenthese!";
    }

    public String get_EXPERROR_WRONG_OPERAND_TYPE() {
        return "Incorrect operand type!";
    }

    public String get_EXPERROR_INCORRECT_ANSWER_OF_POWER_FUNCTION_OPERANDS() {
        return "Incorrect answer of power function operand!";
    }

    public String get_EXPERROR_INCORRECT_BINARY_OPERATOR() {
        return "Incorrect binary operator!";
    }

    public String get_EXPERROR_INCORRECT_MONADIC_OPERATOR() {
        return "Incorrect monadic operator!";
    }

    public String get_EXPERROR_LACK_OPERAND() {
        return "Lack of operand!";
    }

    public String get_EXPERROR_CAN_NOT_IDENTIFIED_CHARACTER() {
        return "Can not identify character!";
    }

    public String get_EXPERROR_DATATYPE_IS_NOT_DEFINED() {
        return "Undefined data type!";
    }

    public String get_EXPERROR_CAN_NOT_CHANGE_A_NOTEXIST_DATUM_TO_ANY_OTHER_DATATYPE() {
        return "Can not change a no-exist DATUM to any other data type!";
    }

    public String get_EXPERROR_NEW_DATATYPE_IS_NOT_DEFINED() {
        return "Undefined new data type!";
    }

    public String get_EXPERROR_INTEGER_OVERFLOW() {
        return "Integer overflow!";
    }

    public String get_EXPERROR_DOUBLE_OVERFLOW() {
        return "Double overflow!";
    }

    public String get_EXPERROR_CHANGE_FROM_DOUBLE_TO_INTEGER_OVERFLOW() {
        return "Overflow when convert double to integer!";
    }

    public String get_EXPERROR_OPERAND_OF_FACTORIAL_MAY_BE_TOO_LARGE() {
        return "Too large operand of factorial!";
    }

    public String get_EXPERROR_DIVISOR_CAN_NOT_BE_ZERO() {
        return "Zero division!";
    }

    public String get_EXPERROR_OPERAND_OF_FACTORIAL_CAN_NOT_LESS_THAN_ZERO() {
        return "Negative operand of factorial!";
    }

    public String get_EXPERROR_OPERAND_OF_BIT_OPERATION_MUST_BE_GREATER_THAN_ZERO() {
        return "No-positive operand of bit operation!";
    }

    public String get_EXPERROR_INCORRECT_NUM_OF_PARAMETER() {
        return "Incorrect number of parameters!";
    }

    public String get_EXPERROR_INVALID_PARAMETER_RANGE() {
        return "Invalid parameter range!";
    }

    public String get_EXPERROR_INVALID_PARAMETER_TYPE() {
        return "Invalid parameter type!";
    }

    public String get_EXPERROR_UNDEFINED_FUNCTION() {
        return "Undefined function!";
    }

    public String get_EXPERROR_UNDEFINED_VARIABLE() {
        return "Undefined variable!";
    }

    public String get_go_button() {
        return "Go!";
    }

    public String get_numbers_operators_button_long() {
        return "numbers\noperators";
    }

    public String get_common_functions_button_long() {
        return "common\nfunctions";
    }

    public String get_more_functions_button_long() {
        return "more\nfunctions";
    }

    public String get_numbers_operators_button_short() {
        return "1+2*3";
    }

    public String get_common_functions_button_short() {
        return "f(x)";
    }

    public String get_more_functions_button_short() {
        return "f()...";
    }

    public String get_left_button() {
        return "Left";
    }

    public String get_right_button() {
        return "Right";
    }

    public String get_eraze_button() {
        return "Eraze";
    }

    public String get_help_button() {
        return "Help";
    }

    public String get_x_help_info() {
        return "X is the name of to-solve variable x or part of name of other to-solve variables like x1, xy, xx etc.";
    }

    public String get_y_help_info() {
        return "Y is the name of to-solve variable y or part of name of other to-solve variables like y3, xy, yyyy etc.";
    }

    public String get_z_help_info() {
        return "Z is the name of to-solve variable z or part of name of other to-solve variables like z2, zx, zzz etc.";
    }

    public String get_assign_help_info() {
        return "Assign operator which assign a value to a variable, e.g. x = 3 + 4 assigns 7 to variable x.";
    }

    public String get_equal_help_info() {
        return "Equal sign which means left part has the same value as right part. For example, x + 1 == 7 + 3 means x + 1 has the same value as 7 + 3 (i.e. 10). Then x can be solved which is 9.";
    }

    public String get_parenthesis_help_info() {
        return "Left parenthesis. Expression part inside a pair of parentheses should be calculated first. For instance, x * (y + 3).";
    }

    public String get_closeparenthesis_help_info() {
        return "Right parenthesis. Expression part inside a pair of parentheses should be calculated first. For instance, x * (y + 3).";
    }

    public String get_squarebracket_help_info() {
        return "Square bracket which starts an array. For example, [[1,2,3],[4,5,6]] is a 2*3 matrix whose first line includes elements 1, 2 and 3 and second line includes elements 4, 5 and 6.";
    }

    public String get_closesquarebracket_help_info() {
        return "Close square bracket which ends an array. For example, [[1,2,3],[4,5,6]] is a 2*3 matrix whose first line includes elements 1, 2 and 3 and second line includes elements 4, 5 and 6.";
    }

    public String get_comma_help_info() {
        return "Comma character which seperates individual elements in an array. For example, [2,3,4,5] has four elements seperated by commas.";
    }

    public String get_plus_help_info() {
        return "Plus sign (support complex number, matrix and string) or positive sign.";
    }

    public String get_minus_help_info() {
        return "Minus sign (support complex number and matrix) or negative sign.";
    }

    public String get_multiplication_help_info() {
        return "Multiplication (support complex number and matrix).";
    }

    public String get_division_help_info() {
        return "Division (support complex number and matrix).";
    }

    public String get_leftdivision_help_info() {
        return "Left division (mainly for matrix to calculator x from Ax=b (i.e. x = A\\b), note that first operand is divisor, do the same operation to division if divisor is not a matrix).";
    }

    public String get_power_help_info() {
        return "Power. Note that both the first and the second operands can be complex number.";
    }

    public String get_exclaimation_help_info() {
        return "Not sign if left unary operator or factorial sign if right unary operator. The operand should be non-negative and will be converted to an integer.";
    }

    public String get_transpose_help_info() {
        return "2D matrix transpose, if operand is a 1D vector, convert it to a 2D matrix, if operand is not an array, return operand itself.";
    }

    public String get_doublequote_help_info() {
        return "Double-quote character which identifies the range of a string.";
    }

    public String get_percentage_help_info() {
        return "Percentage. For example, 403.77% = 4.0377.";
    }

    public String get_bit_and_help_info() {
        return "Bit wise and, which only accepts non-negative integer operands.";
    }

    public String get_bit_or_help_info() {
        return "Bit wise or, which only accepts non-negative integer operands.";
    }

    public String get_bit_xor_help_info() {
        return "Bit wise xor, which only accepts non-negative integer operands.";
    }

    public String get_bit_not_help_info() {
        return "Bit wise not, which only accepts non-negative integer operand.";
    }

    public String get_image_i_help_info() {
        return "Image unit for complex values.";
    }

    public String get_pi_constant_help_info() {
        return "The constant value of PI.";
    }

    public String get_e_constant_help_info() {
        return "The constant value of e. Note that e is also used in scientific notation, e.g. 1.23e-002.";
    }

    public String get_null_constant_help_info() {
        return "Null value.";
    }

    public String get_true_constant_help_info() {
        return "Boolean true value.";
    }

    public String get_false_constant_help_info() {
        return "Boolean false value.";
    }

    public String get_inf_constant_help_info() {
        return "Infinite value.";
    }

    public String get_infi_constant_help_info() {
        return "Infinite image value.";
    }

    public String get_nan_constant_help_info() {
        return "Indefinite value, like 0/0.";
    }

    public String get_nani_constant_help_info() {
        return "Indefinite image value.";
    }

    public String get_no_quick_help_info() {
        return "No quick help information for ";
    }

    public String get_calculator_settings() {
        return "Calculator Settings";
    }

    public String get_digits_shown() {
        return " digits";
    }

    public String get_let_calculator_decide() {
        return "Let calculator decide";
    }

    public String get_never_sci_notation() {
        return "Never use scientific notation";
    }

    public String get_always_sci_notation() {
        return "Always use scientific notation";
    }

    public String get_if_log10_abs_result() {
        return "If log10(abs(result)) >= ";
    }

    public String get_records_shown() {
        return " records";
    }

    public String get_enable_btn_press_vibration_prompt() {
        return "Vibrate when press calculator button";
    }

    public String get_external_storage_mnt_folder_prompt() {
        return "External storage device (e.g. SD card) mounted at folder :";
    }

    public String get_app_folder_prompt() {
        return "Application data folder in external storage device is :";
    }

    public String get_script_folder_prompt() {
        return "User defined functions are stored in external storage device folder :";
    }

    public String get_chart_folder_prompt() {
        return "User generated charts are stored in external storage device folder :";
    }

    public String get_settings_saved() {
        return "Settings saved";
    }

    public String get_select_record_title() {
        return "Calculation Records";
    }

    public String get_no_records() {
        return "No record!";
    }

    public String get_select_input_type() {
        return "Please select expression or answer to input";
    }

    public String get_error_answer_shown() {
        return "Error";
    }

    public String get_return_nothing_answer_shown() {
        return "returns nothing";
    }

    public String get_variables_declared_shown() {
        return "The following variables declared: ";
    }

    public String get_calculator_help() {
        return "Calculator Help";
    }

    public String get_cmd_line_title() {
        return "Command line";
    }

    public String get_cmd_line_welcome_message() {
        return "Please input an expression or help followed by a function name, then press enter.";
    }

    public String get_command_prompt() {
        return "$>";
    }

    public String get_last_cmd() {
        return "Last command";
    }

    public String get_new_script() {
        return "New script";
    }

    public String get_manage_scripts() {
        return "Manage script";
    }

    public String get_view_charts() {
        return "View charts";
    }

    public String get_error() {
        return "Error!";
    }

    public String get_warning() {
        return "Warning!";
    }

    public String get_ok() {
        return "OK";
    }

    public String get_cancel() {
        return "Cancel";
    }

    public String get_yes() {
        return "Yes";
    }

    public String get_no() {
        return "No";
    }

    public String get_close() {
        return "Close";
    }

    public String get_script_file_manager_title() {
        return "Script File Manager";
    }

    public String get_chart_file_manager_title() {
        return "Chart File Manager";
    }

    public String get_file_manager_title() {
        return "File Manager";
    }

    public String get_script_editor_title() {
        return "Script Editor";
    }

    public String get_script_file_saved() {
        return "File saved";
    }

    public String get_file_folder_icon() {
        return "File or folder icon";
    }

    public String get_file_manager_menu_new() {
        return "New";
    }

    public String get_file_manager_menu_open() {
        return "Open";
    }

    public String get_file_manager_menu_save() {
        return "Save";
    }

    public String get_file_manager_menu_rename() {
        return "Rename";
    }

    public String get_file_manager_menu_delete() {
        return "Delete";
    }

    public String get_file_manager_invalid_path() {
        return "Invalid path!";
    }

    public String get_example_scripts_not_loaded() {
        return "Example folder is only for examples. Any change in this folder and its sub-folders will not be applied！";
    }

    public String get_file_manager_new_file_type_script_file() {
        return "New script file";
    }

    public String get_file_manager_new_file_type_folder() {
        return "New folder";
    }

    public String get_file_manager_new_file_prompt() {
        return "Please enter the name :";
    }

    public String get_file_manager_new_file_title() {
        return "Create new item";
    }

    public String get_file_manager_new_file_failed_msg() {
        return "Cannot create the new item!";
    }

    public String get_file_manager_file_rename_title() {
        return "Rename";
    }

    public String get_file_manager_file_new_name_prompt() {
        return "Please enter the new name :";
    }

    public String get_file_manager_file_rename_failed_msg() {
        return "Cannot rename the item!";
    }

    public String get_file_manager_file_delete_title() {
        return "Delete";
    }

    public String get_file_manager_file_delete_confirm() {
        return "Are you sure to delete ";
    }

    public String get_file_manager_file_delete_failed_msg() {
        return "Cannot delete the item!";
    }

    public String get_file_editor_file_open_fail() {
        return "Cannot open file!";
    }

    public String get_file_not_found() {
        return "File cannot be found!";
    }

    public String get_file_editor_file_io_error() {
        return "IO error!";
    }

    public String get_file_editor_file_changed() {
        return "File has been changed, save it?";
    }

    public String get_file_editor_new_file() {
        return "New file";
    }

    public String get_file_editor_file() {
        return "File";
    }

    public String get_file_editor_inside() {
        return "in";
    }

    public String get_file_editor_menu_new() {
        return "New";
    }

    public String get_file_editor_menu_open() {
        return "Open";
    }

    public String get_file_editor_menu_save() {
        return "Save";
    }

    public String get_file_editor_menu_save_as() {
        return "Save as";
    }

    public String get_file_editor_menu_goto_line() {
        return "Go to line";
    }

    public String get_file_editor_goto_line() {
        return "Go to line";
    }

    public String get_plot_2d_title() {
        return "Plot 2D chart";
    }

    public String get_graph_name_prompt() {
        return "Chart name:";
    }

    public String get_graph_title_prompt() {
        return "Chart title:";
    }

    public String get_fill_surface() {
        return "Fill surface";
    }

    public String get_is_surface_grid() {
        return "Do not fill surface (grid only)";
    }

    public String get_graph_Xtitle_prompt() {
        return "X title:";
    }

    public String get_graph_Ytitle_prompt() {
        return "Y title:";
    }

    public String get_graph_Ztitle_prompt() {
        return "Z title:";
    }

    public String get_graph_Rtitle_prompt() {
        return "R title:";
    }

    public String get_graph_show_grid_chkbox_prompt() {
        return "Show\ngrid";
    }

    public String get_add_curve_btn_text() {
        return "Add curve";
    }

    public String get_clear_all_btn_text() {
        return "Clear";
    }

    public String get_generate_chart_btn_text() {
        return "View";
    }

    public String get_curve_title_prompt() {
        return "Title:";
    }

    public String get_curve_color_prompt() {
        return "Color:";
    }

    public String get_curve_point_style_prompt() {
        return "Point style:";
    }

    public String get_curve_show_line_chkbox_prompt() {
        return "Show line";
    }

    public String get_max_color_prompt() {
        return "Color at max value (front and back):";
    }

    public String get_max_color_value_prompt() {
        return "Max value for color selection:";
    }

    public String get_min_color_prompt() {
        return "Color at min value (front and back):";
    }

    public String get_min_color_value_prompt() {
        return "Min value for color selection:";
    }

    public String get_t_from_prompt() {
        return "t: from";
    }

    public String get_t_to_prompt() {
        return "to";
    }

    public String get_t_step_prompt() {
        return "step";
    }

    public String get_u_from_prompt() {
        return "u: from";
    }

    public String get_u_to_prompt() {
        return "to";
    }

    public String get_u_step_prompt() {
        return "step";
    }

    public String get_v_from_prompt() {
        return "v: from";
    }

    public String get_v_to_prompt() {
        return "to";
    }

    public String get_v_step_prompt() {
        return "step";
    }

    public String get_delete_curve_btn_text() {
        return "Delete curve";
    }

    public String get_clear_curve_btn_text() {
        return "Clear curve definition";
    }

    public String get_circle_point_style() {
        return "Circle";
    }

    public String get_triangle_point_style() {
        return "Triangle";
    }

    public String get_square_point_style() {
        return "Square";
    }

    public String get_diamond_point_style() {
        return "Diamond";
    }

    public String get_x_point_style() {
        return "X";
    }

    public String get_point_point_style() {
        return "Point";
    }

    public String get_black_color() {
        return "Black";
    }

    public String get_blue_color() {
        return "Blue";
    }

    public String get_cyan_color() {
        return "Cyan";
    }

    public String get_dkgray_color() {
        return "DKGray";
    }

    public String get_gray_color() {
        return "Gray";
    }

    public String get_green_color() {
        return "Green";
    }

    public String get_ltgray_color() {
        return "LTGray";
    }

    public String get_magenta_color() {
        return "Magenta";
    }

    public String get_orange_color() {
        return "Orange";
    }

    public String get_pink_color() {
        return "Pink";
    }

    public String get_red_color() {
        return "Red";
    }

    public String get_transparent_color() {
        return "Transparent";
    }

    public String get_white_color() {
        return "White";
    }

    public String get_yellow_color() {
        return "Yellow";
    }

    public String get_graph_settings_wrong() {
        return "Chart settings have problem so that it cannot be plotted!";
    }

    public String get_graph_file_cannot_be_read() {
        return "Error occurred when read chart file!";
    }

    public String get_graph_file_cannot_be_saved() {
        return "Error occurred when save chart file!";
    }

    public String get_please_wait() {
        return "Please wait";
    }

    public String get_calculating_chart_data() {
        return "Calculating data to plot chart...";
    }

    public String get_integrate_title() {
        return "Integrate";
    }

    public String get_integrated_expr_prompt() {
        return "Integrated\nExpression:";
    }

    public String get_you_want_to_calculate() {
        return "You want to calculate:";
    }

    public String get_x_variable_name() {
        return "First variable name:";
    }

    public String get_y_variable_name() {
        return "Second variable name:";
    }

    public String get_z_variable_name() {
        return "Third variable name:";
    }

    public String get_variable_from() {
        return "from";
    }

    public String get_variable_to() {
        return "to";
    }

    public String get_variable_number_of_steps() {
        return "number\nof steps";
    }

    public String get_integ_settings_wrong() {
        return "Integrate inputs have problem so that it cannot be integrated!";
    }

    public String get_calculating_integrating_result() {
        return "Calculating integrating result...";
    }

    public String get_integrating_result() {
        return "Result";
    }

    public String get_cannot_create_app_folder() {
        return "The application folder cannot be created. Users will not be able to save their scripts or view charts!";
    }

    public String get_cannot_create_script_folder() {
        return "The script folder cannot be created. Users will not be able to save their scripts!";
    }

    public String get_cannot_create_chart_folder() {
        return "The chart folder cannot be created. Users will not be able to view charts!";
    }

    public String get_function_name() {
        return "Function name";
    }

    public String get_function_info() {
        return "Function info";
    }

    public String get_all_functions() {
        return "all functions";
    }

    public String get_builtin_functions() {
        return "built-in functions";
    }

    public String get_predefined_functions() {
        return "predefined functions";
    }

    public String get_integer_operation_functions() {
        return "integer operation functions";
    }

    public String get_logic_functions() {
        return "logic functions";
    }

    public String get_statistic_and_stochastic_functions() {
        return "statistic and stochastic functions";
    }

    public String get_trigononmetric_functions() {
        return "trigononmetric functions";
    }

    public String get_exponential_and_logarithmic_functions() {
        return "exponential and logrithmic functions";
    }

    public String get_complex_number_functions() {
        return "complex number functions";
    }

    public String get_system_functions() {
        return "system functions";
    }

    public String get_array_or_matrix_functions() {
        return "array or matrix functions";
    }

    public String get_graphic_functions() {
        return "graphic functions";
    }

    public String get_expression_and_integration_functions() {
        return "expression_and_integration functions";
    }

    public String get_string_functions() {
        return "string functions";
    }

    public String get_hyperbolic_trigononmetric_functions() {
        return "hyperbolic_trigononmetric functions";
    }

    public String get_sorting_functions() {
        return "sorting functions";
    }

    public String get_others_functions() {
        return "others functions";
    }

    public String get_exception() {
        return "Exception!";
    }

    public String get_back_to_normal() {
        return "Back to normal.";
    }

    public String get_unmounted_external_storage() {
        return "External storage device is unmounted or removed!";
    }

    public String get_mounting_external_storage() {
        return "Mounting external storage. May lead to calculation interruption.";
    }

    public String get_mounted_external_storage() {
        return "External storage device has been mounted.";
    }

    // added for Java program
    public String get_wrong_file_type() {
        return "Wrong file type!";
    }

    public String get_cannot_open_file() {
        return "Cannot open file!";
    }

    public String get_cannot_find_file() {
        return "Cannot find file!";
    }

    public String get_error_in_analyzing_help_requirement() {
        return "Error in analyzing help requirement!";
    }

    public String get_select_folder() {
        return "Select folder";
    }

    public String get_copy_to_main_window() {
        return "Copy to main window";
    }

    public String get_app_quick_introduction() {
        return "This is a powerful tool to do mathmatic analysis.";
    }

    public String get_product_version() {
        return "Product version:";
    }

    public String get_product_vendor() {
        return "Vendor:";
    }

    public String get_product_homepage() {
        return "Homepage:";
    }

    public String get_menu_file() {
        return "File";
    }

    public String get_menu_reload_lib() {
        return "Reload lib";
    }

    public String get_menu_edit() {
        return "Edit";
    }

    public String get_menu_interrupt_cmd() {
        return "Terminate running task";
    }

    public String get_menu_wrap_line() {
        return "Wrap line";
    }

    public String get_menu_select_all() {
        return "Select all";
    }

    public String get_menu_copy() {
        return "Copy";
    }

    public String get_menu_paste() {
        return "Paste";
    }

    public String get_menu_clear() {
        return "Clear";
    }

    public String get_menu_exit() {
        return "Exit";
    }

    public String get_menu_tools() {
        return "Tools";
    }

    public String get_menu_howto() {
        return "How to ...";
    }

    public String get_menu_get_constant() {
        return "Get constant value";
    }

    public String get_menu_convert_unit() {
        return "Convert unit";
    }

    public String get_menu_calc_polynomial_roots() {
        return "Calculate roots of polynomial";
    }

    public String get_menu_plot_multixy_graph() {
        return "Plot 2D graph";
    }

    public String get_menu_plot_polar_graph() {
        return "Plot Polar graph";
    }

    public String get_menu_plot_multixyz_graph() {
        return "Plot 3D graph";
    }

    public String get_menu_view_chart() {
        return "View chart";
    }

    public String get_menu_content() {
        return "Content";
    }

    public String get_menu_about() {
        return "About";
    }

    public String get_about() {
        return "About";
    }

    public String get_integration_input_title() {
        return "Integration settings";
    }

    public String get_XY_chart_def_title() {
        return "Plot 2D chart";
    }

    public String get_Polar_chart_def_title() {
        return "Plot Polar chart";
    }

    public String get_XYZ_chart_def_title() {
        return "Plot 3D chart";
    }

    public String get_settings_dialog_title() {
        return "Settings";
    }

    public String get_select_user_defined_functions_folder_prompt() {
        return "Select user-defined functions folder:";
    }

    public String get_select_charts_folder_prompt() {
        return "Select folder for charts:";
    }

    public String get_select_btn_text() {
        return "Select";
    }

    public String get_setting_scientific_notation_thresh_prompt() {
        return "Use scientific notation to show result if larger than:";
    }

    public String get_single_line_integrated_expr_prompt() {
        return "Integrated expression:";
    }

    public String get_indefinite_integral() {
        return "Antiderivative";
    }

    public String get_single_integral() {
        return "Single integral";
    }

    public String get_double_integral() {
        return "Double integral";
    }

    public String get_triple_integral() {
        return "Triple integral";
    }

    public String get_wait_for_calculation() {
        return "Calculating task is running. After it is finished, ";
    }

    public String get_help_get_constant_info() {
        return "please run \"help get_constant\" for more information!";
    }

    public String get_help_convert_unit_info() {
        return "please run \"help convert_unit\" for more information!";
    }

    public String get_help_calc_polynomial_roots_info() {
        return "please run \"help roots\" for more information!";
    }

    public String get_interrupt_running_cmd() {
        return "Ctrl-D is pressed and running command is terminated!";
    }

    public String get_session_starts() {
        return "Session starts";
    }

    public String get_session_returns() {
        return "Session returns";
    }

    public String get_cannot_find_asset_files() {
        return "Asset files are missing. User may not be able to use some pre-defined functions or manual.";
    }

    public String get_find_corrupted_asset_files() {
        return "Some asset files can not be successfully created. User may not be able to use some pre-defined functions or manual.";
    }

    public String get_starting() {
        return "Starting ...";
    }

    public String get_unzipping_asset_files() {
        return "Unzipping asset files ...";
    }

    public String get_loading_functions() {
        return "Loading functions ...";
    }

    public String get_receive_following_errors() {
        return "The following error messages were received during starting :";
    }

    public String get_cannot_load_opengl_libs() {
        return "3D openGL charts will not be able to be plotted because related libs cannot be loaded.";
    }

    public String get_ogl_chart_cannot_plot_lack_system_libs() {
        return "3D openGL charts cannot be plotted because lack of platform related libs, please contact developer for the libs.";
    }

    public String get_multiXYZ_config() {
        return "Config 3D chart";
    }

    public String get_multiXYZ_settings_zoom_prompt() {
        return "Zoom whole chart";
    }

    public String get_multiXYZ_settings_x_zoom_prompt() {
        return "Scale x";
    }

    public String get_multiXYZ_settings_y_zoom_prompt() {
        return "Scale y";
    }

    public String get_multiXYZ_settings_z_zoom_prompt() {
        return "Scale z";
    }

    public String get_multiXYZ_settings_x_shift_prompt() {
        return "Shift x";
    }

    public String get_multiXYZ_settings_y_shift_prompt() {
        return "Shift y";
    }

    public String get_multiXYZ_settings_z_shift_prompt() {
        return "Shift z";
    }

    public String get_multiXYZ_settings_x_rotate_prompt() {
        return "Rotate vertically (degree)";
    }

    public String get_multiXYZ_settings_y_rotate_prompt() {
        return "Rotate horizontally (degree)";
    }

    public String get_multiXYZ_settings_z_rotate_prompt() {
        return "Rotate anti-clockwise (degree)";
    }

    public String get_input_invalid_number() {
        return "Input is not a valid number!";
    }

    public String get_input_number_invalid_range() {
        return "Input number is not in valid range! It should not be ";
    }

    public String get_apply() {
        return "Apply";
    }

    public String get_3DExpr_config() {
        return "Config 3D expression chart";
    }

    public String get_XYExpr_config() {
        return "Config 2D x-y expression chart";
    }

    public String get_PolarExpr_config() {
        return "Config Polar r-angle expression chart";
    }

    public String get_detect_singular_points() {
        return "Detect singular points";
    }

    public String get_app_in_a_read_only_folder() {
        return "Please copy the whole AnMath folder into a read-write enabled folder and rerun it.";
    }

    public String get_miss_asset_or_app_in_a_zipped_folder_or_unmapped_usb() {
        return "Please ensure no file (e.g. assets.zip) is missing and copy the whole AnMath folder\ninto a read-write enabled folder and rerun it.";
    }

    public String get_app_in_a_read_only_folder_full() {
        return "Java based Scientific Calculator Plus may be located in a read-only folder.\nPlease copy the whole AnMath folder into a read-write enabled folder and rerun it.";
    }

    public String get_miss_asset_or_app_in_a_zipped_folder_or_unmapped_usb_full() {
        return "Some system files (e.g. assets.zip) may be missing or Java based Scientific\nCalculator Plus may be in a zipped folder or the USB driver of mobile device does\nnot support running Java based Scientific CalculatorPlus from mobile storage.\nPlease ensure no file is missing and copy the whole AnMath folder into a read-write\nenabled folder and rerun it.";
    }

    public String get_plot_chart_variable_range_prompt() {
        return "Variable range to plot chart using function like plot_exprs: ";
    }

    public String get_plot_chart_variable_range_to_prompt() {
        return "to";
    }

    public String get_Polar_chart_r_range_note() {
        return "Note: r must be from 0 to a positive value.";
    }

    public String get_Polar_chart_angle_range_note() {
        return "Note: angle is based on";
    }

    public String get_degree() {
        return "degree";
    }

    public String get_radius() {
        return "radius";
    }

    public String get_not_show_axis_and_title() {
        return "Do not show axises and title";
    }

    public String get_not_show_axis() {
        return "Do not show axises";
    }

    public String get_not_show_title() {
        return "Do not show title";
    }

    public String get_application_name_prompt() {
        return "Please input the application name. Application name should be no more than 32 bytes, which means 32 English letters or at most (could be less than) 16 Unicode characters (Chinese, Japanese and Korean etc.).";
    }

    public String get_application_pkg_id_prompt() {
        return "Please input your application package id with exactly 20 characters. It should be an unique id for your application if you want to publish your app in Google play or any other distribution sites. This id should only include English letters, numbers and dots while dot cannot be the first or the last letter and cannot be adjacent to each other, and number can not be the first or immediately after dot, and between two dots there should be at most 10 letters or numbers. com.cyzapps.AnMFPApp is an example.";
    }

    public String get_application_version_prompt() {
        return "Please input your application version code (left input) and version number (right input). Application version code is a string of ten English letters, dots or number characters, like 1.0.0.0001 , while application version number is a positive integer no greater than 65535.";
    }

    public String get_application_icon_selector_prompt() {
        return "Please select your application icon. Icon file should be a square shape and at least 128 * 128 png file. If you do not select, we will assign a default icon to your application. The selected icon will be shown in the select button.";
    }

    public String get_application_working_folder_prompt() {
        return "Please enter your application default working folder in SD card";
    }

    public String get_application_working_folder_hint() {
        return "If no input, default working folder will be the last part of your application package id, i.e. package id is com.cyzapps.MFPApp, default working folder will be MFPApp in SD card.";
    }

    public String get_selected_icon_invalid() {
        return "Icon file should be a square shape and at least 128 * 128 png file.";
    }

    public String get_select() {
        return "Select";
    }

    public String get_application_description_prompt() {
        return "Please briefly describe your application (e.g. what the application does, what user need input etc.). This is not help or mannual. Its length should be smaller than 256 characters. It will be shown at application start. You can leave it blank.";
    }

    public String get_go_to_next() {
        return "Continue";
    }

    public String get_mfpapp_prog_name() {
        return "Build MFP App";
    }

    public String get_long_click_to_open() {
        return "Long click to open a file or folder";
    }

    public String get_app_name_invalid() {
        return "Invalid application name!";
    }

    public String get_app_pkg_id_invalid() {
        return "Invalid application package Id!";
    }

    public String get_app_ver_str_invalid() {
        return "Invalid application version string!";
    }

    public String get_app_ver_code_invalid() {
        return "Invalid application version code!";
    }

    public String get_app_working_folder_invalid() {
        return "Working folder name is invalid!";
    }

    public String get_help_use_default_app_description_or_type() {
        return "Use automatically generated application description as help page when user selects help menu. Default help page will be created from function help info with your email and website address included. If you do not check this box, please type your application help in the following box:";
    }

    public String get_function_name_prompt() {
        return "Function name";
    }

    public String get_function_name_invalid() {
        return "Function name invalid!";
    }

    public String get_function_description_prompt() {
        return "Please briefly describe your function (e.g. what the function does, what user need input etc.). This is not help or mannual. Its length should be smaller than 1024 characters. It will be shown at application start. You can leave it blank.";
    }

    public String get_function_description_invalid() {
        return "Function description is too long!";
    }

    public String get_function_help() {
        return "Function help";
    }

    public String get_information_about_parameter() {
        return "Information about the parameter";
    }

    public String get_parameter_default_value() {
        return "Parameter default value";
    }

    public String get_with_optional_params() {
        return "With optional parameters";
    }

    public String get_param_is_a_string() {
        return "Treat parameter as a string";
    }

    public String get_param_needs_no_input() {
        return "Parameter needs no input";
    }

    public String get_add() {
        return "Add";
    }

    public String get_delete() {
        return "Delete";
    }

    public String get_delete_all() {
        return "Delete all";
    }

    public String get_please_input_apk_name() {
        return "Please input your apk file name. Apk file will be generated in apks folder in your app directory (AnMath folder).";
    }

    public String get_select_apk_signiture_key() {
        return "Select apk signature";
    }

    public String get_please_input_keystore_name() {
        return "Please input keystore name. Keystore is a file located in signkeys folder in your app directory (AnMath folder).";
    }

    public String get_please_input_keystore_password() {
        return "Keystore password";
    }

    public String get_please_input_password_again() {
        return "Password again";
    }

    public String get_please_input_key_name() {
        return "Key name";
    }

    public String get_please_input_key_valid_period() {
        return "Valid years";
    }

    public String get_please_input_key_password() {
        return "Key password";
    }

    public String get_please_input_your_personal_information() {
        return "Please input your personal or your company information. This will be included in the signature. You can leave some of fields blank if you want.";
    }

    public String get_please_input_your_name() {
        return "Your name";
    }

    public String get_please_input_your_department() {
        return "Your department";
    }

    public String get_please_input_your_company() {
        return "Your company";
    }

    public String get_please_input_your_street_no() {
        return "Your street address";
    }

    public String get_please_input_your_city() {
        return "Your city";
    }

    public String get_please_input_your_state() {
        return "Your state";
    }

    public String get_please_input_your_state_code() {
        return "Your state code";
    }

    public String get_please_input_your_country() {
        return "Your country";
    }

    public String get_please_input_your_contact_details() {
        return "Please input your contact details. The information you input will be shown in app help. You can leave them blank if you want.";
    }

    public String get_please_input_your_email() {
        return "Your email";
    }

    public String get_please_input_your_website() {
        return "Your website";
    }

    public String get_test_key() {
        return "test key";
    }

    public String get_new_key() {
        return "new key";
    }

    public String get_cannot_delete_and_recreate_apk_tmp_folder() {
        return "Cannot delete and recreate temp folder to generate apk file.\nYou may manually delete the apk_generation_temp_folder_0041357 folder in apks directory and try again.";
    }

    public String get_cannot_create_apk_file() {
        return "Cannot create apk file!";
    }

    public String get_do_you_want_to_replace_same_name_file() {
        return "Do you want to replace same name file?";
    }

    public String get_cannot_replace_existing_keystore_file() {
        return "Cannot replace existing keystore file!";
    }

    public String get_invalid_keystore_file() {
        return "Invalid keystore file!";
    }

    public String get_invalid_keystore_password() {
        return "Invalid keystore password!";
    }

    public String get_password_requirement() {
        return "Password should be at least 8 character long and only include letters and numbers.";
    }

    public String get_invalid_keystore_again_password() {
        return "Two inputs of keystore password do not match each other!";
    }

    public String get_invalid_key_name() {
        return "Invalid key name!";
    }

    public String get_invalid_key_valid_period() {
        return "Key valid period should be at least 30 years!";
    }

    public String get_invalid_key_password() {
        return "Invalid key password!";
    }

    public String get_invalid_key_again_password() {
        return "Two inputs of key password do not match each other!";
    }

    public String get_cannot_create_key() {
        return "Cannot create key!";
    }

    public String get_done() {
        return "Done";
    }

    public String get_apk_is_created() {
        return "Apk installation package file is created. You can install it or share the file with other people.\nIf you signed the apk file with a public key (any key created by you or other people),\nyou can publish it in Google play or any other distribution site.\nThe apk file is saved at ";
    }

    public String get_help() {
        return "Help";
    }

    public String get_brief_introduction() {
        return "This application is created from an MFP function. Application introduction, usage of the MFP function and(or) developer contact details are listed as below.";
    }

    public String get_email_address() {
        return "Email address";
    }

    public String get_web_site() {
        return "Web-site";
    }

    public String get_developer_contact_details() {
        return "Contact details of developer";
    }

    public String get_cannot_sign_apk() {
        return "Cannot sign apk!";
    }

    public String get_creating_and_signing_apk() {
        return "Creating and signing apk ...";
    }

    public String get_install() {
        return "Install";
    }

    public String get_share() {
        return "Share";
    }

    public String get_back() {
        return "Back";
    }

    public String get_step_1() {
        return "Step 1";
    }

    public String get_step_2() {
        return "Step 2";
    }

    public String get_step_3() {
        return "Step 3";
    }

    public String get_compilation_terminated_unexpectedly() {
        return "Compilation terminated unexpectedly!";
    }

    public String get_undefined_function() {
        return "Undefined function";
    }

    public String get_line() {
        return "Line";
    }

    public String get_app_working_folder() {
        return "App working folder";
    }

    public String get_your_sd_card() {
        return "Your SD card";
    }

    public String get_list_citingspace_info() {
        return "All citingspaces are listed as below from highest to lowest priority. An ! before citingspace name means it cannot be deleted.";
    }

    public String get_add_citingspace_succeeded() {
        return "Citingspace has been added. Now it has the highest priority.";
    }

    public String get_add_citingspace_failed() {
        return "Citingspace cannot be added.";
    }

    public String get_delete_citingspace_succeeded() {
        return "Citingspace has been deleted.";
    }

    public String get_delete_citingspace_failed() {
        return "Citingspace cannot be deleted.";
    }

    public String get_invalid_shellman_command() {
        return "Invalid shellman command.";
    }

    public String get_add_citingspace_succeeded2() {
        return "Citingspace has been added. Now it has higher priority than any other citingspace except the top one.";
    }

    public String get_shellman_command_need_cs_parameter() {
        return "Shellman command needs a citingspace parameter.";
    }

    public String get_1st_order_derivative() {
        return "1st derivative";
    }

    public String get_2nd_order_derivative() {
        return "2nd derivative";
    }

    public String get_3rd_order_derivative() {
        return "3rd derivative";
    }

    public String get_derivative_expr_prompt() {
        return "Expression";
    }

    public String get_variable_name() {
        return "Variable name";
    }

    public String get_variable_value() {
        return "Variable value";
    }

    public String get_menu_calculus() {
        return "Calculus";
    }

    public String get_menu_derivative() {
        return "Derivative";
    }

    public String get_derivative_input_title() {
        return "Derivative settings";
    }

    public String get_cannot_find_settings_file_use_default() {
        return "Cannot get settings file. Use default";
    }

    public String get_interrupt_running_task() {
        return "Interrupt running task";
    }

    public String get_terminate_session() {
        return "Terminate session";
    }

    public String get_ctrl_c_not_supported() {
        return "Ctrl-C is not supported in this platform. User cannot interrupt running task.";
    }

    public String get_invalid_command_option() {
        return "Invalid command option.";
    }

    public String get_no_script_file_name() {
        return "No script file name.";
    }

    public String get_invalid_lib_path() {
        return "Invalid lib path.";
    }

    public String get_no_lib_path() {
        return "No lib path.";
    }

    public String get_invalid_number_of_parameters() {
        return "Invalid number of parameters.";
    }

    public String get_invalid_entry_function() {
        return "Invalid entry function. Has @execution_entry been correctly declared in the file?";
    }

    public String get_command_UI_help() {
        return "Usage:\n-c (or /c): console interactive mode;\n-g (or /g): GUI interactive mode;\n-L (or /L): add a user mfps lib folder. A command line may include several -L (or /L)s;\n-f (or /f): run a script file. The argument after the flag is file name. The following argments are parameters to run the script. This flag must be the last flag in the command;\n-i (or /i): Print usage of a script. The next argument after the flag is script file name. This flag must be the last flag in the command;\n-v (or /v): show version;\n-h (or /h): show this help.";
    }

    public String get_gui_not_supported() {
        return "Graphic User Interface is not supported.";
    }
    
    public String get_not_a_folder() {
        return "not a folder";
    }

    public String get_call() {
        return "call";
    }

    public String get_additional_user_defined_libs_prompt() {
        return "Additional user-defined libs (folder or mfps file, one line each):";
    }

    public String get_additional_usr_lib_folder_text() {
        return "Select folder";
    }

    public String get_additional_usr_lib_mfps_text() {
        return "Select MFPS file";
    }

    public String get_select_file() {
        return "Select file";
    }

    public String get_variable_has_been_added() {
        return "Variable has been added.";
    }

    public String get_shellman_command_need_parameter() {
        return "Shellman command needs parameter.";
    }

    public String get_variable_has_been_deleted() {
        return "Variable has been deleted.";
    }

    public String get_variable_not_exist() {
        return "Variable does not exist.";
    }

    public String get_file() {
        return "File";
    }

    public String get_variable_not_exist_or_cannot_delete() {
        return "Variable does not exist or cannot be deleted.";
    }

    public String get_do_you_want_to_exit() {
        return "Do you want to exit?";
    }
}
