package util;

public interface ConstantUtil {
    String MENU = """
                    === 医疗挂号预约系统 ===
                    1 预约挂号
                    2 查询信息
                    3 取消预约
                    4 退出系统
                    请选择>\s""";
    String INPUT_INFO = """
                    请按以下格式输入预约信息：
                    [患者姓名] [医生姓名] [科室名称] [预约日期 yyyy-MM-dd] [预约时间段 HH:mm:ss]
                    请输入>\s""";
    String INPUT_ID_OR_NAME = "请输入预约id或患者姓名> ";
    String INPUT_ID = "请输入预约id> ";
    String BANNER = "id\t\t患者姓名\t\t医生姓名\t\t科室名称\t\t预约日期\t\t预约时间段";
    String INVALID_INPUT = "无效输入！";
    String OPERATION_SUCCESS = "操作成功！";
    String OPERATION_FAILURE = "操作失败！";
}
