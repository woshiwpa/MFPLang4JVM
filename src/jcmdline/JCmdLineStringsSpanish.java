/*
 * MFP project, JCmdLineStringsSpanish.java : Designed and developed by Tony Cui in 2021
 */
package jcmdline;

/**
 * Clases para soporte multi lenguaje
 */
public class JCmdLineStringsSpanish extends JCmdLineStrings {

    @Override
    public String get_hello() {
        return "Hola Mundo, ActivityMain!";
    }

    @Override
    public String get_app_name() {
        return "Scientific Calculator Plus para Java";
    }

    @Override
    public String get_alert_dialog_ok() {
        return "OK";
    }

    @Override
    public String get_alert_dialog_cancel() {
        return "Cancelar";
    }

    @Override
    public String get_menu_cmdline() {
        return "Línea de comandos";
    }

    @Override
    public String get_menu_plotgraph() {
        return "Trazar gráfica 2D";
    }

    @Override
    public String get_menu_integrate() {
        return "Integrar";
    }

    @Override
    public String get_menu_history() {
        return "Histórico";
    }

    @Override
    public String get_menu_settings() {
        return "Configuraciónión";
    }

    @Override
    public String get_menu_help() {
        return "Ayuda";
    }

    @Override
    public String get_setting_number_format_prompt() {
        return "Definir bits de precisión:";
    }

    @Override
    public String get_setting_record_length_prompt() {
        return "Definir longitud del registro:";
    }

    @Override
    public String get_setting_extreme_value_prompt() {
        return "Definir notación científica";
    }

    @Override
    public String get_whats_new() {
        return "Lo nuevo";
    }

    @Override
    public String get_do_not_show_whats_new_again() {
        return "No mostrar este cuadro de diálogo la próxima vez.";
    }

    @Override
    public String get_welcome_message() {
        return "Escriba ayuda o una expresión como 3 + log(4.1 / avg(1,5,-3)), luego oprima ejecutar!";
    }

    @Override
    public String get_EXPERROR_NO_EXPRESSION() {
        return "Falta expresión!";
    }

    @Override
    public String get_EXPERROR_MULTIPLE_EXPRESSIONS() {
        return "Expresions múltiples!";
    }

    @Override
    public String get_EXPERROR_LACK_OPERATOR_BETWEEN_TWO_OPERANDS() {
        return "Falta operador entre dos operandos!";
    }

    @Override
    public String get_EXPERROR_NUM_CAN_NOT_HAVE_TWO_DECIMAL_POINT() {
        return "Dos puntos decimales en un número!";
    }

    @Override
    public String get_EXPERROR_NUM_DO_NOT_ACCORD_DECIMAL_NUM_WRITING_STANDARD() {
        return "No hay coincidencia con la escritura de números decimales estándar!";
    }

    @Override
    public String get_EXPERROR_OPERATOR_NOT_EXIST() {
        return "Operador no existe!";
    }

    @Override
    public String get_EXPERROR_UNMATCHED_RIGHTPARENTHESE() {
        return "Falta paréntesis izquierdo que coincida con el derecho!";
    }

    @Override
    public String get_EXPERROR_UNMATCHED_LEFTPARENTHESE() {
        return "Falta paréntesis derecho que coincida con el izquierdo!";
    }

    @Override
    public String get_EXPERROR_WRONG_OPERAND_TYPE() {
        return "Tipo de operando incorrecto!";
    }

    @Override
    public String get_EXPERROR_INCORRECT_ANSWER_OF_POWER_FUNCTION_OPERANDS() {
        return "Repuesta incorrecta del operando de la función potencia!";
    }

    @Override
    public String get_EXPERROR_INCORRECT_BINARY_OPERATOR() {
        return "Operador binario incorrecto!";
    }

    @Override
    public String get_EXPERROR_INCORRECT_MONADIC_OPERATOR() {
        return "Operador monádico incorrecto!";
    }

    @Override
    public String get_EXPERROR_LACK_OPERAND() {
        return "Falta operando!";
    }

    @Override
    public String get_EXPERROR_CAN_NOT_IDENTIFIED_CHARACTER() {
        return "Imposible identificar el caracter!";
    }

    @Override
    public String get_EXPERROR_DATATYPE_IS_NOT_DEFINED() {
        return "Tipo de dato indefinido!";
    }

    @Override
    public String get_EXPERROR_CAN_NOT_CHANGE_A_NOTEXIST_DATUM_TO_ANY_OTHER_DATATYPE() {
        return "No se puede cambiar un dato no-exist a cualquier otro tipo de dato!";
    }

    @Override
    public String get_EXPERROR_NEW_DATATYPE_IS_NOT_DEFINED() {
        return "Nuevo tipo de dato indefinido!";
    }

    @Override
    public String get_EXPERROR_INTEGER_OVERFLOW() {
        return "Desbordamiento de número entero!";
    }

    @Override
    public String get_EXPERROR_DOUBLE_OVERFLOW() {
        return "Desbordamiento de número doble!";
    }

    @Override
    public String get_EXPERROR_CHANGE_FROM_DOUBLE_TO_INTEGER_OVERFLOW() {
        return "Desbordamiento al convertir número doble a entero!";
    }

    @Override
    public String get_EXPERROR_OPERAND_OF_FACTORIAL_MAY_BE_TOO_LARGE() {
        return "Operando demasiado grande para la función factorial!";
    }

    @Override
    public String get_EXPERROR_DIVISOR_CAN_NOT_BE_ZERO() {
        return "División por cero!";
    }

    @Override
    public String get_EXPERROR_OPERAND_OF_FACTORIAL_CAN_NOT_LESS_THAN_ZERO() {
        return "Operando negativo en la función factorial!";
    }

    @Override
    public String get_EXPERROR_OPERAND_OF_BIT_OPERATION_MUST_BE_GREATER_THAN_ZERO() {
        return "Operando No-positivo en una operación de bit!";
    }

    @Override
    public String get_EXPERROR_INCORRECT_NUM_OF_PARAMETER() {
        return "Número de parámetros incorrecto!";
    }

    @Override
    public String get_EXPERROR_INVALID_PARAMETER_RANGE() {
        return "Rango de parámetro no válido!";
    }

    @Override
    public String get_EXPERROR_INVALID_PARAMETER_TYPE() {
        return "Tipo de parámetro no válido!";
    }

    @Override
    public String get_EXPERROR_UNDEFINED_FUNCTION() {
        return "Función indefinida!";
    }

    @Override
    public String get_EXPERROR_UNDEFINED_VARIABLE() {
        return "Variable indefinida!";
    }

    @Override
    public String get_go_button() {
        return "Go!";
    }

    @Override
    public String get_numbers_operators_button_long() {
        return "números\noperadores";
    }

    @Override
    public String get_common_functions_button_long() {
        return "Funciones\ncomunes";
    }

    @Override
    public String get_more_functions_button_long() {
        return "Más\nfunciones";
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
        return "Izquierda";
    }

    @Override
    public String get_right_button() {
        return "Derecha";
    }

    @Override
    public String get_eraze_button() {
        return "Borrar";
    }

    @Override
    public String get_help_button() {
        return "Ayuda";
    }

    @Override
    public String get_x_help_info() {
        return "X es el nombre de la variable a resolver, a graficar o parte del nombre de otra variable como x1, xy, xx etc.";
    }

    @Override
    public String get_y_help_info() {
        return "Y es el nombre de la variable a resolver, a graficar o parte del nombre de otra variable como like y3, xy, yyyy etc.";
    }

    @Override
    public String get_z_help_info() {
        return "Z es el nombre de la variable a resolver, a graficar o parte del nombre de otra variable como z2, zx, zzz etc.";
    }

    @Override
    public String get_assign_help_info() {
        return "Operqador de asignación, el cual asigna un valor a una variable, ejemplo. x = 3 + 4 asigna 7 a la variable x.";
    }

    @Override
    public String get_equal_help_info() {
        return "Signo igual el cual significa que la expresión de la derecha tiene el mismo valor que la expresión de la derecha. Por ejemplo, x + 1 == 7 + 3 significa x + 1 tiene el mismo valor que 7 + 3 (i.e. 10). Por lo tanto, x puede ser resuelta y su valor es 9.";
    }

    @Override
    public String get_parenthesis_help_info() {
        return "Paréntesis izquierdo. La expresión dentro de los paréntesis debería ser calculada primero. Por ejemplo, x * (y + 3).";
    }

    @Override
    public String get_closeparenthesis_help_info() {
        return "Paréntesis derecho. La expresión dentro de los paréntesis debería ser calculada primero. Por ejemplo, x * (y + 3).";
    }

    @Override
    public String get_squarebracket_help_info() {
        return "Corchete de apertura indica el comienzo de un arreglo. Por ejemplo, [[1,2,3],[4,5,6]] es una matriz de 2*3 cuya primera línea incluye los elementos 1, 2 y 3 y la segunda línea incluye los elementos 4, 5 y 6.";
    }

    @Override
    public String get_closesquarebracket_help_info() {
        return "Corchete de cierre indica el fin de un arreglo. Por ejemplo, [[1,2,3],[4,5,6]] es una matriz de 2*3 cuya primera línea incluye los elementos 1, 2 y 3 y la segunda línea incluye los elementos 4, 5 y 6.";
    }

    @Override
    public String get_comma_help_info() {
        return "Coma caracter que sepera elementos individuales de un arreglo. Por ejemplo, el vector [2,3,4,5] tiene 4 elementos separados por comas.";
    }

    @Override
    public String get_plus_help_info() {
        return "Signo mas (soporta números complejos, matrices y cadenas) o signo positivo.";
    }

    @Override
    public String get_minus_help_info() {
        return "Signo menos (soporta números complejos, matrices y cadenas) o signo negativo.";
    }

    @Override
    public String get_multiplication_help_info() {
        return "Multiplicación (soporta números complejos y matrices).";
    }

    @Override
    public String get_division_help_info() {
        return "División (soporta números complejos y matrices).";
    }

    @Override
    public String get_leftdivision_help_info() {
        return "División izquierda (principalmente para matrices para calcular x de Ax=b (i.e. x = A\\b), note que el primer operando es el divisor y realiza la misma operación de división si el divisor no es una matriz).";
    }

    @Override
    public String get_power_help_info() {
        return "Potencia. Note que tanto el primer operando como el segundo pueden ser números complejos.";
    }

    @Override
    public String get_exclaimation_help_info() {
        return "Signo no (negación) si actúa como operador unario izquierdo o factorial si es operador unario derecho. El operando deberá ser no negativo y será convertido a entero.";
    }

    @Override
    public String get_transpose_help_info() {
        return "Matriz transpuesta 2D, si el operando es un vector 1D, convertirlo a una matriz 2D, si el operando no es un arreglo, retorna el operando mismo.";
    }

    @Override
    public String get_doublequote_help_info() {
        return "Comilla doble caracter que delimita una cadena.";
    }

    @Override
    public String get_percentage_help_info() {
        return "Porcentaje. Por ejemplo, 403.77% = 4.0377.";
    }

    @Override
    public String get_bit_and_help_info() {
        return "Operador binario(bit a bit) Y. Sólo acepta operandos enteros  no-negativos.";
    }

    @Override
    public String get_bit_or_help_info() {
        return "Operador binario(bit a bit) O. Sólo acepta operandos enteros  no-negativos.";
    }

    @Override
    public String get_bit_xor_help_info() {
        return "Operador binario(bit a bit) O exclusivo (XOR). Sólo acepta operandos enteros  no-negativos.";
    }

    @Override
    public String get_bit_not_help_info() {
        return "Operador binario(bit a bit) NO. Sólo acepta operandos enteros  no-negativos.";
    }

    @Override
    public String get_image_i_help_info() {
        return "Unidad imagen para valores complejos.";
    }

    @Override
    public String get_pi_constant_help_info() {
        return "Valor de la constante PI.";
    }

    @Override
    public String get_e_constant_help_info() {
        return "Valor de la constante e. Note que e también se usa en notación científica, por ejemplo 1.23e-002.";
    }

    @Override
    public String get_null_constant_help_info() {
        return "Valor nulo.";
    }

    @Override
    public String get_true_constant_help_info() {
        return "Valor booleano VERDADERO.";
    }

    @Override
    public String get_false_constant_help_info() {
        return "Valor booleano FALSO.";
    }

    @Override
    public String get_inf_constant_help_info() {
        return "Valor infinito.";
    }

    @Override
    public String get_infi_constant_help_info() {
        return "Valor de imagen para infinito.";
    }

    @Override
    public String get_nan_constant_help_info() {
        return "Valor indefinido, como 0/0.";
    }

    @Override
    public String get_nani_constant_help_info() {
        return "Valor de imagen para indefinido.";
    }

    @Override
    public String get_no_quick_help_info() {
        return "No existe ayuda rápida para ";
    }

    @Override
    public String get_calculator_settings() {
        return "Configurar la calculadora";
    }

    @Override
    public String get_digits_shown() {
        return " digitos";
    }

    @Override
    public String get_let_calculator_decide() {
        return "Permitir que la calculadora decida";
    }

    @Override
    public String get_never_sci_notation() {
        return "Nunca usar notación científica";
    }

    @Override
    public String get_always_sci_notation() {
        return "Siempre usar notación científica";
    }

    @Override
    public String get_if_log10_abs_result() {
        return "Si log10(abs(resultado)) >= ";
    }

    @Override
    public String get_records_shown() {
        return " registros";
    }

    @Override
    public String get_enable_btn_press_vibration_prompt() {
        return "Vibrar al oprimir el botón calcular";
    }

    @Override
    public String get_external_storage_mnt_folder_prompt() {
        return "Dispositivo de almacenamiento externo(por ejemplo tarjeta SD) montado en carpeta :";
    }

    @Override
    public String get_app_folder_prompt() {
        return "Carpeta de datos de aplicación en un dispositivo de almacenamiento externo es :";
    }

    @Override
    public String get_script_folder_prompt() {
        return "Las funciones definidas por el usuario se almancenan en la carpeta del dispositivo de almacenamiento externo :";
    }

    @Override
    public String get_chart_folder_prompt() {
        return "Las gráficas generadas por el usuario se almancenan en la carpeta del dispositivo de almacenamiento externo :";
    }

    @Override
    public String get_settings_saved() {
        return "Configuración guardada";
    }

    @Override
    public String get_select_record_title() {
        return "Registros de cálculo";
    }

    @Override
    public String get_no_records() {
        return "No hay registros!";
    }

    @Override
    public String get_select_input_type() {
        return "Por favor, seleccione una opción o ingrrese el dato solicitado";
    }

    @Override
    public String get_error_answer_shown() {
        return "Error";
    }

    @Override
    public String get_return_nothing_answer_shown() {
        return "Respuesta nula";
    }

    @Override
    public String get_variables_declared_shown() {
        return "Las siguientes variables declaradas :";
    }

    @Override
    public String get_calculator_help() {
        return "Ayuda de la calculadora";
    }

    @Override
    public String get_cmd_line_title() {
        return "Línea de comandos";
    }

    @Override
    public String get_cmd_line_welcome_message() {
        return "Por favro, ingrese una expresión o escriba ayuda/help seguida por el nombre de una función, luego oprima ENTER.";
    }

    @Override
    public String get_command_prompt() {
        return "$>";
    }

    @Override
    public String get_last_cmd() {
        return "Último comando";
    }

    @Override
    public String get_new_script() {
        return "Nueva secuencia de comandos";
    }

    @Override
    public String get_manage_scripts() {
        return "Administrar secuencia de comandos";
    }

    @Override
    public String get_view_charts() {
        return "Ver gráficas";
    }

    @Override
    public String get_error() {
        return "Error!";
    }

    @Override
    public String get_warning() {
        return "Precaución!";
    }

    @Override
    public String get_ok() {
        return "OK";
    }

    @Override
    public String get_cancel() {
        return "Cancelar";
    }

    @Override
    public String get_yes() {
        return "Si";
    }

    @Override
    public String get_no() {
        return "No";
    }

    @Override
    public String get_close() {
        return "Cerrar";
    }

    @Override
    public String get_script_file_manager_title() {
        return "Administador de archivos de secuencias de comandos";
    }

    @Override
    public String get_chart_file_manager_title() {
        return "Administrador de archivos de gráficas";
    }

    @Override
    public String get_file_manager_title() {
        return "Administrador de archivos";
    }

    @Override
    public String get_script_editor_title() {
        return "Editor de secuencias de comandos";
    }

    @Override
    public String get_script_file_saved() {
        return "Archivo guardado";
    }

    @Override
    public String get_file_folder_icon() {
        return "Ícono de archivo o carpeta";
    }

    @Override
    public String get_file_manager_menu_new() {
        return "Nuevow";
    }

    @Override
    public String get_file_manager_menu_open() {
        return "Abrir";
    }

    @Override
    public String get_file_manager_menu_save() {
        return "Guardar";
    }

    @Override
    public String get_file_manager_menu_rename() {
        return "Renombrar";
    }

    @Override
    public String get_file_manager_menu_delete() {
        return "Borrar";
    }

    @Override
    public String get_file_manager_invalid_path() {
        return "Ruta incorrecta!";
    }

    @Override
    public String get_example_scripts_not_loaded() {
        return "La carpeta Ejemplos es sólo para ejemplos. Cualquier cambio en esta carperta o sus sub carpetas no se aplicará！";
    }

    @Override
    public String get_file_manager_new_file_type_script_file() {
        return "Nuevo archivo de secuencia de comandos";
    }

    @Override
    public String get_file_manager_new_file_type_folder() {
        return "Nueva carperta";
    }

    @Override
    public String get_file_manager_new_file_prompt() {
        return "Por favor entre el nombre :";
    }

    @Override
    public String get_file_manager_new_file_title() {
        return "Crear nuevo ítem";
    }

    @Override
    public String get_file_manager_new_file_failed_msg() {
        return "No se puede crear el nuevo ítem!";
    }

    @Override
    public String get_file_manager_file_rename_title() {
        return "Renombrar";
    }

    @Override
    public String get_file_manager_file_new_name_prompt() {
        return "Por favor entre el nuevo nombre :";
    }

    @Override
    public String get_file_manager_file_rename_failed_msg() {
        return "No se puede renombrar el ítem!";
    }

    @Override
    public String get_file_manager_file_delete_title() {
        return "Borrar";
    }

    @Override
    public String get_file_manager_file_delete_confirm() {
        return "Está seguro de borrar? ";
    }

    @Override
    public String get_file_manager_file_delete_failed_msg() {
        return "No se puede borrar el ítem!";
    }

    @Override
    public String get_file_editor_file_open_fail() {
        return "No se puede abrir el archivo!";
    }

    @Override
    public String get_file_not_found() {
        return "Archivo no encontrado!";
    }

    @Override
    public String get_file_editor_file_io_error() {
        return "Error de entrada/saldia!";
    }

    @Override
    public String get_file_editor_file_changed() {
        return "El archvio ha sido cambiado, desea guardarlo?";
    }

    @Override
    public String get_file_editor_new_file() {
        return "Nuevo archivo";
    }

    @Override
    public String get_file_editor_file() {
        return "Archivo";
    }

    @Override
    public String get_file_editor_inside() {
        return "en";
    }

    @Override
    public String get_file_editor_menu_new() {
        return "Nuevo";
    }

    @Override
    public String get_file_editor_menu_open() {
        return "Abrir";
    }

    @Override
    public String get_file_editor_menu_save() {
        return "Guardar";
    }

    @Override
    public String get_file_editor_menu_save_as() {
        return "Guardar como";
    }

    @Override
    public String get_file_editor_menu_goto_line() {
        return "Ir a la línea";
    }

    @Override
    public String get_file_editor_goto_line() {
        return "Ir a la línea";
    }

    @Override
    public String get_plot_2d_title() {
        return "Trazar gráfica 2D";
    }

    @Override
    public String get_graph_name_prompt() {
        return "Nombre de gráfica:";
    }

    @Override
    public String get_graph_title_prompt() {
        return "Título de gráfica:";
    }

    @Override
    public String get_fill_surface() {
        return "Rellenar superficie";
    }

    @Override
    public String get_is_surface_grid() {
        return "No rellenar superficie (sólo rejilla)";
    }

    @Override
    public String get_graph_Xtitle_prompt() {
        return "Título del eje X:";
    }

    @Override
    public String get_graph_Ytitle_prompt() {
        return "Título del eje Y";
    }

    @Override
    public String get_graph_Ztitle_prompt() {
        return "Título del eje Z:";
    }

    @Override
    public String get_graph_Rtitle_prompt() {
        return "Título del eje R:";
    }

    @Override
    public String get_graph_show_grid_chkbox_prompt() {
        return "Mostrar\nrejilla";
    }

    @Override
    public String get_add_curve_btn_text() {
        return "Añadir curva";
    }

    @Override
    public String get_clear_all_btn_text() {
        return "Borrar";
    }

    @Override
    public String get_generate_chart_btn_text() {
        return "Ver";
    }

    @Override
    public String get_curve_title_prompt() {
        return "Título:";
    }

    @Override
    public String get_curve_color_prompt() {
        return "Color:";
    }

    @Override
    public String get_curve_point_style_prompt() {
        return "Estilo del punto:";
    }

    @Override
    public String get_curve_show_line_chkbox_prompt() {
        return "Mostrar línea";
    }

    @Override
    public String get_max_color_prompt() {
        return "Color a su valor máximo (frente y dorso):";
    }

    @Override
    public String get_max_color_value_prompt() {
        return "Valor máximo para la selección de color:";
    }

    @Override
    public String get_min_color_prompt() {
        return "Color a su valor mínimo (frente y dorso):";
    }

    @Override
    public String get_min_color_value_prompt() {
        return "Valor mínimo para la selección de color:";
    }

    @Override
    public String get_t_from_prompt() {
        return "t: desde";
    }

    @Override
    public String get_t_to_prompt() {
        return "hasta";
    }

    @Override
    public String get_t_step_prompt() {
        return "incremento";
    }

    @Override
    public String get_u_from_prompt() {
        return "u: desde";
    }

    @Override
    public String get_u_to_prompt() {
        return "to";
    }

    @Override
    public String get_u_step_prompt() {
        return "incremento";
    }

    @Override
    public String get_v_from_prompt() {
        return "v: desde";
    }

    @Override
    public String get_v_to_prompt() {
        return "hasta";
    }

    @Override
    public String get_v_step_prompt() {
        return "incremento";
    }

    @Override
    public String get_delete_curve_btn_text() {
        return "Borrar curva";
    }

    @Override
    public String get_clear_curve_btn_text() {
        return "Borrar definición de curva";
    }

    @Override
    public String get_circle_point_style() {
        return "Círculo";
    }

    @Override
    public String get_triangle_point_style() {
        return "Triángulo";
    }

    @Override
    public String get_square_point_style() {
        return "Cuadrado";
    }

    @Override
    public String get_diamond_point_style() {
        return "Diamante";
    }

    @Override
    public String get_x_point_style() {
        return "X";
    }

    @Override
    public String get_point_point_style() {
        return "Punto";
    }

    @Override
    public String get_black_color() {
        return "Negro";
    }

    @Override
    public String get_blue_color() {
        return "Azul";
    }

    @Override
    public String get_cyan_color() {
        return "Cian";
    }

    @Override
    public String get_dkgray_color() {
        return "Gris oscuro";
    }

    @Override
    public String get_gray_color() {
        return "Gris";
    }

    @Override
    public String get_green_color() {
        return "Verde";
    }

    @Override
    public String get_ltgray_color() {
        return "Gris claro";
    }

    @Override
    public String get_magenta_color() {
        return "Magenta";
    }

    @Override
    public String get_orange_color() {
        return "Naranja";
    }

    @Override
    public String get_pink_color() {
        return "Rosa";
    }

    @Override
    public String get_red_color() {
        return "Rojo";
    }

    @Override
    public String get_transparent_color() {
        return "Transparente";
    }

    @Override
    public String get_white_color() {
        return "Blanco";
    }

    @Override
    public String get_yellow_color() {
        return "Amarillo";
    }

    @Override
    public String get_graph_settings_wrong() {
        return "La configuración de la gráfica tiene problemas y no puede ser trazada!";
    }

    @Override
    public String get_graph_file_cannot_be_read() {
        return "Ocurrió un error al abrir el archivo de la gráfica!";
    }

    @Override
    public String get_graph_file_cannot_be_saved() {
        return "Ocurrió un error al guardar el archivo de la gráfica!";
    }

    @Override
    public String get_please_wait() {
        return "Por favor espere";
    }

    @Override
    public String get_calculating_chart_data() {
        return "Calculando datos para trazar la gráfica...";
    }

    @Override
    public String get_integrate_title() {
        return "Integrar";
    }

    @Override
    public String get_integrated_expr_prompt() {
        return "Expresión\nIntegrada:";
    }

    @Override
    public String get_you_want_to_calculate() {
        return "Desea calcular?:";
    }

    @Override
    public String get_x_variable_name() {
        return "Nombre de la primera variable:";
    }

    @Override
    public String get_y_variable_name() {
        return "Nombre de la segunda variable:";
    }

    @Override
    public String get_z_variable_name() {
        return "Nombre de la tercera variable:";
    }

    @Override
    public String get_variable_from() {
        return "desde";
    }

    @Override
    public String get_variable_to() {
        return "hasta";
    }

    @Override
    public String get_variable_number_of_steps() {
        return "número\nde pasos";
    }

    @Override
    public String get_integ_settings_wrong() {
        return "Los parámetros de integración tienen problemas de modo que no se puede integrar!";
    }

    @Override
    public String get_calculating_integrating_result() {
        return "Calculando resultado de integración...";
    }

    @Override
    public String get_integrating_result() {
        return "Resultado";
    }

    @Override
    public String get_cannot_create_app_folder() {
        return "La carpeta de la aplicación no se puede crear. Los usuarios no podrán guardar sus secuencias de comandos o ver sus gráficas!";
    }

    @Override
    public String get_cannot_create_script_folder() {
        return "La carpeta de secuencias de comandos no se puede crear. Los usuarios no podrán guardar sus secuencias de comandos!    ";
    }

    @Override
    public String get_cannot_create_chart_folder() {
        return "La carpeta de gráficas no se puede crear. Los usuarios no podrán ver sus gráficas!";
    }

    @Override
    public String get_function_name() {
        return "Nombre de función";
    }

    @Override
    public String get_function_info() {
        return "Información de función";
    }

    @Override
    public String get_all_functions() {
        return "todas las funciones";
    }

    @Override
    public String get_builtin_functions() {
        return "funciones integradas";
    }

    @Override
    public String get_predefined_functions() {
        return "funciones predefinidas";
    }

    @Override
    public String get_integer_operation_functions() {
        return "funciones para operaciones con enteros";
    }

    @Override
    public String get_logic_functions() {
        return "funciones lógicas";
    }

    @Override
    public String get_statistic_and_stochastic_functions() {
        return "funciones estadísticas y estocásticas";
    }

    @Override
    public String get_trigononmetric_functions() {
        return "funciones trigonométricas";
    }

    @Override
    public String get_exponential_and_logarithmic_functions() {
        return "funciones logarítimicas y exponenciales";
    }

    @Override
    public String get_complex_number_functions() {
        return "funciones con números complejos";
    }

    @Override
    public String get_system_functions() {
        return "funciones del sistema";
    }

    @Override
    public String get_array_or_matrix_functions() {
        return "funciones de arreglos o matrices";
    }

    @Override
    public String get_graphic_functions() {
        return "funciones gráficas";
    }

    @Override
    public String get_expression_and_integration_functions() {
        return "Funciones y expresiones de integración";
    }

    @Override
    public String get_string_functions() {
        return "funcions de cadena";
    }

    @Override
    public String get_hyperbolic_trigononmetric_functions() {
        return "funciones trigronométricas hiperbólicas";
    }

    @Override
    public String get_sorting_functions() {
        return "funciones de ordenación";
    }

    @Override
    public String get_others_functions() {
        return "otras funciones";
    }

    @Override
    public String get_exception() {
        return "Excepción!";
    }

    @Override
    public String get_back_to_normal() {
        return "Volver a normal.";
    }

    @Override
    public String get_unmounted_external_storage() {
        return "El almacenamiento externo no ha sido montado o fue removido!";
    }

    @Override
    public String get_mounting_external_storage() {
        return "Montando almacenamiento externo. Los cálculos podrían interrumpirse.";
    }

    @Override
    public String get_mounted_external_storage() {
        return "Dispositivo de almacenamiento externo montado.";
    }

    // added for Java program
    @Override
    public String get_wrong_file_type() {
        return "Tipo de archivo incorrecto!";
    }

    @Override
    public String get_cannot_open_file() {
        return "No se puede abrir el archivo!";
    }

    @Override
    public String get_cannot_find_file() {
        return "Archivo no encontrado!";
    }

    @Override
    public String get_error_in_analyzing_help_requirement() {
        return "Error analizando los requerimientos de ayuda!";
    }

    @Override
    public String get_select_folder() {
        return "Seleccionar carpeta";
    }

    @Override
    public String get_copy_to_main_window() {
        return "Copiar a la ventana principal";
    }

    @Override
    public String get_app_quick_introduction() {
        return "Esta es una poderosa para realizar análisis matemático.";
    }

    @Override
    public String get_product_version() {
        return "Versión del producto:";
    }

    @Override
    public String get_product_vendor() {
        return "Vendedor:";
    }

    @Override
    public String get_product_homepage() {
        return "Homepage:";
    }

    @Override
    public String get_menu_file() {
        return "Archivo";
    }

    @Override
    public String get_menu_reload_lib() {
        return "Recargar lib";
    }

    @Override
    public String get_menu_edit() {
        return "Editar";
    }

    @Override
    public String get_menu_interrupt_cmd() {
        return "Finalizar la tarea en ejecución";
    }

    @Override
    public String get_menu_wrap_line() {
        return "Ajuste de línea";
    }

    @Override
    public String get_menu_select_all() {
        return "Seleccionar todo";
    }

    @Override
    public String get_menu_copy() {
        return "Copiar";
    }

    @Override
    public String get_menu_paste() {
        return "Pegar";
    }

    @Override
    public String get_menu_clear() {
        return "Borrar";
    }

    @Override
    public String get_menu_exit() {
        return "Salir";
    }

    @Override
    public String get_menu_tools() {
        return "Herramientas";
    }

    @Override
    public String get_menu_howto() {
        return "Como...";
    }

    @Override
    public String get_menu_get_constant() {
        return "Obteber valor constante";
    }

    @Override
    public String get_menu_convert_unit() {
        return "Convertir unidad";
    }

    @Override
    public String get_menu_calc_polynomial_roots() {
        return "Calcular raices del polinomio";
    }

    @Override
    public String get_menu_plot_multixy_graph() {
        return "Trazar gráfica 2D";
    }

    @Override
    public String get_menu_plot_polar_graph() {
        return "Trazar gráfica de coordenadas polares";
    }

    @Override
    public String get_menu_plot_multixyz_graph() {
        return "Trazar gráfica 3D";
    }

    @Override
    public String get_menu_view_chart() {
        return "Ver gráfica";
    }

    @Override
    public String get_menu_content() {
        return "Contenido";
    }

    @Override
    public String get_menu_about() {
        return "Acerca de";
    }

    @Override
    public String get_about() {
        return "Acerca de";
    }

    @Override
    public String get_integration_input_title() {
        return "Configuraciones de integración";
    }

    @Override
    public String get_XY_chart_def_title() {
        return "Trazar gráfica 2D";
    }

    @Override
    public String get_Polar_chart_def_title() {
        return "Trazar gráfica de coordenaas polares";
    }

    @Override
    public String get_XYZ_chart_def_title() {
        return "Trazar gráfica 3D";
    }

    @Override
    public String get_settings_dialog_title() {
        return "Configuración";
    }

    @Override
    public String get_select_user_defined_functions_folder_prompt() {
        return "Seleccionar carpeta de funciones definidas por el usuario:";
    }

    @Override
    public String get_select_charts_folder_prompt() {
        return "Seleccionar carpeta de gráficas:";
    }

    @Override
    public String get_select_btn_text() {
        return "Seleccionar";
    }

    @Override
    public String get_setting_scientific_notation_thresh_prompt() {
        return "Usar notación científica para mostrar el resultado si es mayor que:";
    }

    @Override
    public String get_single_line_integrated_expr_prompt() {
        return "Expresión integrada:";
    }

    @Override
    public String get_single_integral() {
        return "Integral indefinida";
    }

    @Override
    public String get_indefinite_integral() {
        return "Integral simple";
    }

    @Override
    public String get_double_integral() {
        return "Integral doble";
    }

    @Override
    public String get_triple_integral() {
        return "Integral triple";
    }

    @Override
    public String get_wait_for_calculation() {
        return "Tarea de cálculo en ejecución. Después de que finalice, ";
    }

    @Override
    public String get_help_get_constant_info() {
        return "por favor ejecute \"ayuda constant\" para más información!";
    }

    @Override
    public String get_help_convert_unit_info() {
        return "por favor ejecute \"ayuda unidad_conversion\" para más información!";
    }

    @Override
    public String get_help_calc_polynomial_roots_info() {
        return "por favor ejecute \"ayuda raices\" para más información!";
    }

    @Override
    public String get_interrupt_running_cmd() {
        return "Ctrl-D ha sido oprimido y el comando en ejecución ha sido interrumpido!";
    }

    @Override
    public String get_session_starts() {
        return "Inicio de sesión";
    }

    @Override
    public String get_session_returns() {
        return "Retorno de sesión";
    }

    @Override
    public String get_cannot_find_asset_files() {
        return "Faltan algunos archivos de activos. Puede que algunas funciones predefinidas o el manual no estén disponibles.";
    }

    @Override
    public String get_find_corrupted_asset_files() {
        return "Algunos archivos de activos no pudieron ser creados. Puede que algunas funciones predefinidas o el manual no estén disponibles.";
    }

    @Override
    public String get_starting() {
        return "Iniciando ...";
    }

    @Override
    public String get_unzipping_asset_files() {
        return "Descomprimiendo archivos de activos ...";
    }

    @Override
    public String get_loading_functions() {
        return "Cargando funciones ...";
    }

    @Override
    public String get_receive_following_errors() {
        return "Los siguientes mensajes de error fueron recibidos durante el inicio de sesión :";
    }

    @Override
    public String get_cannot_load_opengl_libs() {
        return "Los gráficos 3D openGL no podrán ser trazados porque las librerías respectivas no pueden ser cargadas.";
    }

    @Override
    public String get_ogl_chart_cannot_plot_lack_system_libs() {
        return "Los gráficos 3D openGL no podrán ser trazados por ausencia de las librerías de la plataforma respectivas, por favor contacte al desarrollador de las librerías.";
    }

    @Override
    public String get_multiXYZ_config() {
        return "Configurar gráficas 3D";
    }

    @Override
    public String get_multiXYZ_settings_zoom_prompt() {
        return "Zoom de la gráfica completa";
    }

    @Override
    public String get_multiXYZ_settings_x_zoom_prompt() {
        return "Escala x";
    }

    @Override
    public String get_multiXYZ_settings_y_zoom_prompt() {
        return "Escala y";
    }

    @Override
    public String get_multiXYZ_settings_z_zoom_prompt() {
        return "Escala z";
    }

    @Override
    public String get_multiXYZ_settings_x_shift_prompt() {
        return "Desplazamiento x";
    }

    @Override
    public String get_multiXYZ_settings_y_shift_prompt() {
        return "Desplazamiento y";
    }

    @Override
    public String get_multiXYZ_settings_z_shift_prompt() {
        return "Desplazamiento z";
    }

    @Override
    public String get_multiXYZ_settings_x_rotate_prompt() {
        return "Rotación vertical (grados)";
    }

    @Override
    public String get_multiXYZ_settings_y_rotate_prompt() {
        return "Rotación horizontal(grados)";
    }

    @Override
    public String get_multiXYZ_settings_z_rotate_prompt() {
        return "Rotación anti horaria(grados)";
    }

    @Override
    public String get_input_invalid_number() {
        return "El número entrado no es válido!";
    }

    @Override
    public String get_input_number_invalid_range() {
        return "El número entrado no está dentro del rango válido! Esto no debería ocurrir ";
    }

    @Override
    public String get_apply() {
        return "Aplicar";
    }

    @Override
    public String get_3DExpr_config() {
        return "Configurar gráfica de expresión 3D";
    }

    @Override
    public String get_XYExpr_config() {
        return "Configurar gráfica de expresión 2D x-y";
    }

    @Override
    public String get_PolarExpr_config() {
        return "Configurar gráfica de expresión de coordenadas polares (r-ángulo)";
    }

    @Override
    public String get_detect_singular_points() {
        return "Deectar puntos singulares";
    }

    @Override
    public String get_app_in_a_read_only_folder() {
        return "Por favor copiar la carpeta AnMath completa en una carpeta con permisos de lectura-escritura y ejecute de nuevo.";
    }

    @Override
    public String get_miss_asset_or_app_in_a_zipped_folder_or_unmapped_usb() {
        return "Por favor, asegúrese de que no falta ningún archivo (ejemplo, assets.zip) y copie la carpeta AnMath completa en una con permisos de lectura-escritura y ejecute de nuevo.";
    }

    @Override
    public String get_app_in_a_read_only_folder_full() {
        return "Scientific Calculator Plus basado en Java puede estar ubicado en una carpeta de sólo lectura.\nPor favor, copie la carpeta AnMath completa en una con permisos de lectura-escritura y ejecute de nuevo.";
    }

    @Override
    public String get_miss_asset_or_app_in_a_zipped_folder_or_unmapped_usb_full() {
        return "Pueden faltar algunos archivos del sistema (ejemplo assets.zip) o Scientific Calculator Plus, basado en Java, puede estar en una carpeta comprimida (.zip) o el controlador USB del dispositivo no soporta la ejecución de Scientific CalculatorPlus, (basado en Java), desde un dispositivo removible.\nPor favor, asegúrese de que no falta ningún archivo y copie la carpeta AnMath completa en otra con permisos de lectura-escritura y ejecute de nuevo.";
    }

    @Override
    public String get_plot_chart_variable_range_prompt() {
        return "Rango variable para trazar gráfica utilizando una función como plot_exprs: ";
    }

    @Override
    public String get_plot_chart_variable_range_to_prompt() {
        return "hasta";
    }

    @Override
    public String get_Polar_chart_r_range_note() {
        return "Nota: r debe estar entre 0 y un valor positivo.";
    }

    @Override
    public String get_Polar_chart_angle_range_note() {
        return "Nota: ángulo basado en:";
    }

    @Override
    public String get_degree() {
        return "grados";
    }

    @Override
    public String get_radius() {
        return "radio";
    }

    @Override
    public String get_not_show_axis_and_title() {
        return "No mostrar axises y título";
    }

    @Override
    public String get_not_show_axis() {
        return "No mostrar axises";
    }

    @Override
    public String get_not_show_title() {
        return "No mostrar título";
    }

    @Override
    public String get_application_name_prompt() {
        return "Por favor introduce el nombre de la aplicación. Nombre de la aplicación debe ser no más de 32 bytes, lo que significa 32 letras inglesas o en la mayoría de 16 caracteres Unicode (chino, japonés y coreano, etc.).";
    }

    @Override
    public String get_application_pkg_id_prompt() {
        return "Por favor, introduzca su ID de aplicación que incluye exactamente 20 caracteres. Este identificador debe ser único si quieres publicar tu aplicación en Google Play o cualquier otros sitios de distribución. Sólo debe incluir los puntos, números y las letras inglesas. Punto no puede ser la primera o la última letra y no puede ser adyacentes entre sí. Número no puede ser el primero o inmediatamente después punto. Entre dos puntos no debería ser como máximo 10 letras o números. com.cyzapps.AnMFPApp es un ejemplo.";
    }

    @Override
    public String get_application_version_prompt() {
        return "Por favor introduzca el código de la versión (entrada izquierda) y el número de versión (entrada derecha). Código de versión de la aplicación es una cadena de diez letras inglesas, puntos o caracteres numéricos, como 1.0.0.0001. Número de versión es un número entero positivo no mayor que 65535.";
    }

    @Override
    public String get_application_icon_selector_prompt() {
        return "Por favor, seleccione el icono de su aplicación. Archivo Icono debe ser una forma cuadrada y por lo menos 128 * 128 archivo png. Si no se selecciona, se le asignará un icono por defecto. El icono seleccionado se mostrará en el botón de selección.";
    }

    @Override
    public String get_application_working_folder_prompt() {
        return "Por favor introduce el directorio de trabajo de su aplicación";
    }

    @Override
    public String get_application_working_folder_hint() {
        return "Si no hay entrada, carpeta de trabajo predeterminada será la última parte del paquete de ID. Por ejemplo, si el paquete ID es com.cyzapps.MFPApp, la carpeta de trabajo será el directorio MFPApp en tu tarjeta SD.";
    }

    @Override
    public String get_selected_icon_invalid() {
        return "Archivo Icono debe ser una forma cuadrada y por lo menos 128 * 128 archivo png.";
    }

    @Override
    public String get_select() {
        return "Seleccionar";
    }

    @Override
    public String get_application_description_prompt() {
        return "Por favor, describa brevemente su aplicación (por ejemplo, lo que se hará, lo que hay a la entrada, etc.). Esto no es un manual. Debe incluir menos de 256 caracteres. Se mostrará en el inicio de la aplicación. Puede dejarlo en blanco.";
    }

    @Override
    public String get_go_to_next() {
        return "Continuar";
    }

    @Override
    public String get_mfpapp_prog_name() {
        return "Construir MFP App";
    }

    @Override
    public String get_long_click_to_open() {
        return "Larga haga clic para abrir un archivo o carpeta";
    }

    @Override
    public String get_app_name_invalid() {
        return "Nombre de la aplicación no es válido!";
    }

    @Override
    public String get_app_pkg_id_invalid() {
        return "Paquete de aplicación no válida Id!";
    }

    @Override
    public String get_app_ver_str_invalid() {
        return "Cadena de versión no válido!";
    }

    @Override
    public String get_app_ver_code_invalid() {
        return "Número de versión no válido!";
    }

    @Override
    public String get_app_working_folder_invalid() {
        return "Trabajar nombre de la carpeta no es válido!";
    }

    @Override
    public String get_help_use_default_app_description_or_type() {
        return "Utilice la página de ayuda generada automáticamente cuando el usuario selecciona el menú de ayuda. Por defecto la página de ayuda se creó a partir de la función de información de ayuda, y también incluye tu correo electrónico y dirección del sitio web. Si no marca esta casilla, por favor escriba su ayuda de la aplicación en el cuadro siguiente. Y la página de ayuda con crear a partir de su descripción.";
    }

    @Override
    public String get_function_name_prompt() {
        return "Nombre de la función";
    }

    @Override
    public String get_function_name_invalid() {
        return "Nombre de la función no es válido!";
    }

    @Override
    public String get_function_description_prompt() {
        return "Por favor, describa brevemente su función (por ejemplo, lo que se hará, lo que hay a la entrada, etc.). Esto no es un manual. Debe incluir menos de 1024 caracteres. Se mostrará en el inicio de la función. Puede dejarlo en blanco.";
    }

    @Override
    public String get_function_description_invalid() {
        return "Descripción de la función es demasiado largo!";
    }

    @Override
    public String get_function_help() {
        return "Función de Ayuda";
    }

    @Override
    public String get_information_about_parameter() {
        return "Información sobre el parámetro";
    }

    @Override
    public String get_parameter_default_value() {
        return "El valor por defecto del parámetro";
    }

    @Override
    public String get_with_optional_params() {
        return "Con parámetros opcionales";
    }

    @Override
    public String get_param_is_a_string() {
        return "Parámetro es una cadena";
    }

    @Override
    public String get_param_needs_no_input() {
        return "Parámetro de entrada no necesita";
    }

    @Override
    public String get_add() {
        return "Añadir";
    }

    @Override
    public String get_delete() {
        return "Borrar";
    }

    @Override
    public String get_delete_all() {
        return "Eliminar todos";
    }

    @Override
    public String get_please_input_apk_name() {
        return "Por favor, introduzca el nombre de su archivo APK. Archivo APK se generará en la carpeta AnMath\\apks.";
    }

    @Override
    public String get_select_apk_signiture_key() {
        return "Seleccionar clave";
    }

    @Override
    public String get_please_input_keystore_name() {
        return "Por favor, introduzca el nombre del almacén de claves. Almacén de claves es un archivo ubicado en el directorio AnMath\\signkeys.";
    }

    @Override
    public String get_please_input_keystore_password() {
        return "La contraseña del almacén de claves";
    }

    @Override
    public String get_please_input_password_again() {
        return "Contraseña de nuevo";
    }

    @Override
    public String get_please_input_key_name() {
        return "Nombre de clave";
    }

    @Override
    public String get_please_input_key_valid_period() {
        return "Años válidos";
    }

    @Override
    public String get_please_input_key_password() {
        return "Contraseña de clave";
    }

    @Override
    public String get_please_input_your_personal_information() {
        return "Por favor, introduzca su información personal o de su empresa. Esto será incluido en el firma. Puede dejar algunos de los campos en blanco si desea.";
    }

    @Override
    public String get_please_input_your_name() {
        return "Tu nombre";
    }

    @Override
    public String get_please_input_your_department() {
        return "Tu departamento";
    }

    @Override
    public String get_please_input_your_company() {
        return "Tu compañía";
    }

    @Override
    public String get_please_input_your_street_no() {
        return "Tu dirección de la calle";
    }

    @Override
    public String get_please_input_your_city() {
        return "Tu ciudad";
    }

    @Override
    public String get_please_input_your_state() {
        return "Tu provincia";
    }

    @Override
    public String get_please_input_your_state_code() {
        return "Tu código de provincia";
    }

    @Override
    public String get_please_input_your_country() {
        return "Tu país";
    }

    @Override
    public String get_please_input_your_contact_details() {
        return "Por favor introduce tus datos de contacto. Esto se muestra en la APP help.You puede dejarlos en blanco si desea.";
    }

    @Override
    public String get_please_input_your_email() {
        return "Tu correo electrónico";
    }

    @Override
    public String get_please_input_your_website() {
        return "Tu sitio web";
    }

    @Override
    public String get_test_key() {
        return "clave de la prueba";
    }

    @Override
    public String get_new_key() {
        return "nueva clave";
    }

    @Override
    public String get_cannot_delete_and_recreate_apk_tmp_folder() {
        return "No se puede eliminar y volver a crear una carpeta temporal para generar el archivo APK.\nEs posible eliminar manualmente la carpeta apk/apk_generation_temp_folder_0041357 y vuelva a intentarlo";
    }

    @Override
    public String get_cannot_create_apk_file() {
        return "No se puede crear el archivo APK!";
    }

    @Override
    public String get_do_you_want_to_replace_same_name_file() {
        return "¿Quieres reemplazar el archivo con el mismo nombre?";
    }

    @Override
    public String get_cannot_replace_existing_keystore_file() {
        return "No se puede reemplazar almacén de claves existente!";
    }

    @Override
    public String get_invalid_keystore_file() {
        return "Almacén de claves no válido!";
    }

    @Override
    public String get_invalid_keystore_password() {
        return "La contraseña del almacén de claves no válido!";
    }

    @Override
    public String get_password_requirement() {
        return "La contraseña debe tener al menos 8 caracteres de longitud y sólo incluyen letras y números.";
    }

    @Override
    public String get_invalid_keystore_again_password() {
        return "Dos entradas de la contraseña del almacén de claves no coinciden!";
    }

    @Override
    public String get_invalid_key_name() {
        return "Nombre de clave no es válida!";
    }

    @Override
    public String get_invalid_key_valid_period() {
        return "Válido período debe ser de al menos 30 años!";
    }

    @Override
    public String get_invalid_key_password() {
        return "Contraseña de clave no válida!";
    }

    @Override
    public String get_invalid_key_again_password() {
        return "Dos entradas de la contraseña de la clave no coinciden!";
    }

    @Override
    public String get_cannot_create_key() {
        return "No se puede crear clave!";
    }

    @Override
    public String get_done() {
        return "Esta hecho";
    }

    @Override
    public String get_apk_is_created() {
        return "Se crea el archivo apk. Se puede instalar o compartir con otras personas.\nSi se firma con una clave pública (cualquier tecla creado por usted u otras personas),\npuede publicar en Google jugar o cualquier otra tienda APP.\nEl archivo apk se guarda en ";
    }

    @Override
    public String get_help() {
        return "Ayuda";
    }

    @Override
    public String get_brief_introduction() {
        return "Esta aplicación se crea a partir de una función de MFP. Introducción de aplicaciones, el uso de la función y desarrollador datos de contacto se enumeran a continuación.";
    }

    @Override
    public String get_email_address() {
        return "Email";
    }

    @Override
    public String get_web_site() {
        return "Sitio web";
    }

    @Override
    public String get_developer_contact_details() {
        return "Datos de contacto del desarrollador";
    }

    @Override
    public String get_cannot_sign_apk() {
        return "No se puede firmar el archivo APK!";
    }

    @Override
    public String get_creating_and_signing_apk() {
        return "Se está creando el archivo APK ...";
    }

    @Override
    public String get_install() {
        return "Instalar";
    }

    @Override
    public String get_share() {
        return "Cuota";
    }

    @Override
    public String get_back() {
        return "Atras";
    }

    @Override
    public String get_step_1() {
        return "Paso 1";
    }

    @Override
    public String get_step_2() {
        return "Paso 2";
    }

    @Override
    public String get_step_3() {
        return "Paso 3";
    }

    @Override
    public String get_compilation_terminated_unexpectedly() {
        return "Compilación abortado!";
    }

    @Override
    public String get_undefined_function() {
        return "Función Indefinido";
    }

    @Override
    public String get_line() {
        return "Línea";
    }

    @Override
    public String get_app_working_folder() {
        return "Carpeta de trabajo App";
    }

    @Override
    public String get_your_sd_card() {
        return "Su tarjeta SD";
    }

    @Override
    public String get_list_citingspace_info() {
        return "Todos los citingspaces se enumeran a continuación de prioridad más alta a la más baja. Un ! antes del nombre del citingspace significa que no se puede eliminar.";
    }

    @Override
    public String get_add_citingspace_succeeded() {
        return "Citingspace ha sido añadido con más alta prioridad";
    }

    @Override
    public String get_add_citingspace_failed() {
        return "Citingspace no se puede añadir.";
    }

    @Override
    public String get_delete_citingspace_succeeded() {
        return "Citingspace ha sido borrada.";
    }

    @Override
    public String get_delete_citingspace_failed() {
        return "Citingspace no se puede eliminar.";
    }

    @Override
    public String get_invalid_shellman_command() {
        return "comando shellman no válido.";
    }

    @Override
    public String get_add_citingspace_succeeded2() {
        return "Citingspace ha sido añadido. Ahora tiene la prioridad mayor que cualquier otro citingspace, excepto el citingspace de nivel superior (TOP LEVEL Citingspace).";
    }

    @Override
    public String get_shellman_command_need_cs_parameter() {
        return "Shellman comando necesita citingspace como parámetro.";
    }

    @Override
    public String get_1st_order_derivative() {
        return "En primer lugar Derivative";
    }

    @Override
    public String get_2nd_order_derivative() {
        return "Segunda derivada";
    }

    @Override
    public String get_3rd_order_derivative() {
        return "Tercera derivada";
    }

    @Override
    public String get_derivative_expr_prompt() {
        return "Expresión";
    }

    @Override
    public String get_variable_name() {
        return "Nombre de la variable";
    }

    @Override
    public String get_variable_value() {
        return "Valor de la variable";
    }

    @Override
    public String get_menu_calculus() {
        return "Cálculo";
    }

    @Override
    public String get_menu_derivative() {
        return "Derivado";
    }

    @Override
    public String get_derivative_input_title() {
        return "Configuraciones de cálculo";
    }

    @Override
    public String get_cannot_find_settings_file_use_default() {
        return "No se puede encontrar el archivo de configuración. Uso por defecto";
    }

    @Override
    public String get_interrupt_running_task() {
        return "Interrumpir la ejecución de tareas";
    }

    @Override
    public String get_terminate_session() {
        return "terminar la sesión";
    }

    @Override
    public String get_ctrl_c_not_supported() {
        return "Ctrl-C no está soportado por el sistema operativo. El usuario no puede interrumpir la ejecución de tareas.";
    }

    @Override
    public String get_invalid_command_option() {
        return "Opción inválida.";
    }

    @Override
    public String get_no_script_file_name() {
        return "El nombre del archivo de secuencia de comandos no se encuentra.";
    }

    @Override
    public String get_invalid_lib_path() {
        return "Directorio de la biblioteca ilegal.";
    }

    @Override
    public String get_no_lib_path() {
        return "Directorio de biblioteca que falta.";
    }

    @Override
    public String get_invalid_number_of_parameters() {
        return "Número incorrecto de argumentos.";
    }

    @Override
    public String get_invalid_entry_function() {
        return "punto de entrada no válido. @execution_entry ha sido declarado correctamente en el archivo?";
    }

    @Override
    public String get_command_UI_help() {
        return "Uso:\n-c (or /c): modo interactivo de consola;\n-g (or /g): Modo interactivo GUI;\n-L (or /L)：Añadir una biblioteca definida por el usuario. Un comando puede incluir múltiples -L banderas. Cada uno de ellos es seguido por una biblioteca definida por el usuario.\n-f (or /f)：Ejecutar un script. El nombre de archivo de guión sigue la opción -f. Después de que el nombre de archivo son los parámetros para ejecutar el script. Tenga en cuenta que este indicador debe ser la última bandera en el comando.\n-i (or /i)：uso de la impresión de un archivo de script. El nombre del archivo es después de la bandera -i. Este indicador debe ser la última bandera en el comando.\n-v (or /v)：Versión para imprimir de software.\n-h (or /h)：Imprimir este mensaje de ayuda.";
    }

    @Override
    public String get_gui_not_supported() {
        return "La interfaz gráfica de usuario no es compatible.";
    }

    @Override
    public String get_not_a_folder() {
        return "no una carpeta";
    }

    @Override
    public String get_call() {
        return "llamada";
    }

    @Override
    public String get_additional_user_defined_libs_prompt() {
        return "librerias adicionales definidos por el usuario (archivo de carpetas o MFP, una línea cada uno):";
    }

    @Override
    public String get_additional_usr_lib_folder_text() {
        return "seleccionar una carpeta";
    }

    @Override
    public String get_additional_usr_lib_mfps_text() {
        return "Seleccione un archivo";
    }

    @Override
    public String get_select_file() {
        return "Seleccione un archivo";
    }

    @Override
    public String get_variable_has_been_added() {
        return "variable se ha añadido.";
    }

    @Override
    public String get_shellman_command_need_parameter() {
        return "Shellman comando necesita parámetro.";
    }

    @Override
    public String get_variable_has_been_deleted() {
        return "Variable ha sido eliminada.";
    }

    @Override
    public String get_variable_not_exist() {
        return "Variable no existe.";
    }

    @Override
    public String get_file() {
        return "archivo";
    }

    @Override
    public String get_variable_not_exist_or_cannot_delete() {
        return "variable no existe o no se puede eliminar.";
    }

    @Override
    public String get_do_you_want_to_exit() {
        return "Desea dejar de todos modos?";
    }
}
