# 医院预约挂号系统
### 项目功能

利用RPC技术实现了一个医院挂号预约系统：

1. 客户端实现用户交互，服务器端实现预约信息的存储和管理。
2. 客户端与服务器端利用RPC机制进行协作。
3. 服务器端暴露了以下RPC接口：
   * bool bookAppointment(Appointment appointment)：预约挂号
   * Appointment queryByID(int appointmentID)：根据预约 ID 查询预约信息
   * List<Appointment> queryByPatient(String patientName)：根据患者姓名查询所有预约
   * bool cancelAppointment(int appointmentID)：取消指定预约

### 实现方式

本项目分别用了`RMI`和`gRPC`这两种RPC技术来实现功能。
