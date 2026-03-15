import { useState } from "react";
import { mailService } from "@/services/admin/mailService";
import { toast } from "sonner";
import { Button } from "@/components/ui/button";

export default function MailPage() {
  const [newUserForm, setNewUserForm] = useState({
    actorId: undefined as number | undefined,
    receiverId: undefined as number | undefined,
    toEmail: "",
    fullName: "",
    email: "",
    password: "",
    roleName: "",
  });

  const [resetForm, setResetForm] = useState({
    actorId: undefined as number | undefined,
    receiverId: undefined as number | undefined,
    toEmail: "",
    fullName: "",
    otp: "",
  });

  const [assignmentForm, setAssignmentForm] = useState({
    actorId: undefined as number | undefined,
    receiverId: undefined as number | undefined,
    toEmail: "",
    staffName: "",
    title: "",
    content: "",
    module: "",
    objectId: undefined as number | undefined,
  });

  const sendNewUserMail = async () => {
    try {
      await mailService.sendNewUserMail(newUserForm);
      toast.success("Gửi mail tài khoản mới thành công");
    } catch (error) {
      console.error(error);
      toast.error("Gửi mail thất bại");
    }
  };

  const sendResetMail = async () => {
    try {
      await mailService.sendResetPasswordMail(resetForm);
      toast.success("Gửi mail OTP thành công");
    } catch (error) {
      console.error(error);
      toast.error("Gửi mail thất bại");
    }
  };

  const sendAssignmentMail = async () => {
    try {
      await mailService.sendAssignmentMail(assignmentForm);
      toast.success("Gửi mail giao việc thành công");
    } catch (error) {
      console.error(error);
      toast.error("Gửi mail thất bại");
    }
  };

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Thông báo / Email</h1>

      <div className="p-5 space-y-3 bg-white border rounded-2xl">
        <h2 className="text-lg font-semibold">Mail tạo user mới</h2>
        <input className="w-full p-3 border rounded-xl" placeholder="Actor ID"
          value={newUserForm.actorId ?? ""}
          onChange={(e) => setNewUserForm({ ...newUserForm, actorId: Number(e.target.value) || undefined })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Receiver ID"
          value={newUserForm.receiverId ?? ""}
          onChange={(e) => setNewUserForm({ ...newUserForm, receiverId: Number(e.target.value) || undefined })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Email nhận"
          value={newUserForm.toEmail}
          onChange={(e) => setNewUserForm({ ...newUserForm, toEmail: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Họ tên"
          value={newUserForm.fullName}
          onChange={(e) => setNewUserForm({ ...newUserForm, fullName: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Email tài khoản"
          value={newUserForm.email}
          onChange={(e) => setNewUserForm({ ...newUserForm, email: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Mật khẩu"
          value={newUserForm.password}
          onChange={(e) => setNewUserForm({ ...newUserForm, password: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Tên role"
          value={newUserForm.roleName}
          onChange={(e) => setNewUserForm({ ...newUserForm, roleName: e.target.value })}
        />
        <Button onClick={sendNewUserMail}>Gửi mail</Button>
      </div>

      <div className="p-5 space-y-3 bg-white border rounded-2xl">
        <h2 className="text-lg font-semibold">Mail OTP reset password</h2>
        <input className="w-full p-3 border rounded-xl" placeholder="Actor ID"
          value={resetForm.actorId ?? ""}
          onChange={(e) => setResetForm({ ...resetForm, actorId: Number(e.target.value) || undefined })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Receiver ID"
          value={resetForm.receiverId ?? ""}
          onChange={(e) => setResetForm({ ...resetForm, receiverId: Number(e.target.value) || undefined })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Email nhận"
          value={resetForm.toEmail}
          onChange={(e) => setResetForm({ ...resetForm, toEmail: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Họ tên"
          value={resetForm.fullName}
          onChange={(e) => setResetForm({ ...resetForm, fullName: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="OTP"
          value={resetForm.otp}
          onChange={(e) => setResetForm({ ...resetForm, otp: e.target.value })}
        />
        <Button onClick={sendResetMail}>Gửi mail</Button>
      </div>

      <div className="p-5 space-y-3 bg-white border rounded-2xl">
        <h2 className="text-lg font-semibold">Mail giao việc cho staff</h2>
        <input className="w-full p-3 border rounded-xl" placeholder="Actor ID"
          value={assignmentForm.actorId ?? ""}
          onChange={(e) => setAssignmentForm({ ...assignmentForm, actorId: Number(e.target.value) || undefined })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Receiver ID"
          value={assignmentForm.receiverId ?? ""}
          onChange={(e) => setAssignmentForm({ ...assignmentForm, receiverId: Number(e.target.value) || undefined })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Email staff"
          value={assignmentForm.toEmail}
          onChange={(e) => setAssignmentForm({ ...assignmentForm, toEmail: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Tên staff"
          value={assignmentForm.staffName}
          onChange={(e) => setAssignmentForm({ ...assignmentForm, staffName: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Tiêu đề"
          value={assignmentForm.title}
          onChange={(e) => setAssignmentForm({ ...assignmentForm, title: e.target.value })}
        />
        <textarea className="w-full p-3 border rounded-xl" placeholder="Nội dung"
          value={assignmentForm.content}
          onChange={(e) => setAssignmentForm({ ...assignmentForm, content: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Module"
          value={assignmentForm.module}
          onChange={(e) => setAssignmentForm({ ...assignmentForm, module: e.target.value })}
        />
        <input className="w-full p-3 border rounded-xl" placeholder="Object ID"
          value={assignmentForm.objectId ?? ""}
          onChange={(e) => setAssignmentForm({ ...assignmentForm, objectId: Number(e.target.value) || undefined })}
        />
        <Button onClick={sendAssignmentMail}>Gửi mail</Button>
      </div>
    </div>
  );
}