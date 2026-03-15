import { useEffect, useState } from "react";
import { profileService } from "@/services/admin/profileService";
import { uploadService } from "@/services/admin/uploadService";
import type {
  ChangePasswordPayload,
  ProfileData,
  UpdateProfilePayload,
} from "@/types/admin/profile";
import { toast } from "sonner";
import { Button } from "@/components/ui/button";

const defaultProfile: UpdateProfilePayload = {
  fullName: "",
  email: "",
  phone: "",
  avatar: "",
};

const defaultPassword: ChangePasswordPayload = {
  oldPassword: "",
  newPassword: "",
  confirmPassword: "",
};

export default function ProfilePage() {
  const [profile, setProfile] = useState<ProfileData | null>(null);
  const [profileForm, setProfileForm] = useState(defaultProfile);
  const [passwordForm, setPasswordForm] = useState(defaultPassword);

  const [loadingProfile, setLoadingProfile] = useState(false);
  const [savingProfile, setSavingProfile] = useState(false);
  const [changingPassword, setChangingPassword] = useState(false);
  const [uploadingAvatar, setUploadingAvatar] = useState(false);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        setLoadingProfile(true);

        const res = await profileService.getMyProfile();
        const data = res.data;

        setProfile(data);
        setProfileForm({
          fullName: data.fullName || "",
          email: data.email || "",
          phone: data.phone || "",
          avatar: data.avatar || "",
        });
      } catch (error) {
        console.error(error);
        toast.error("Tải hồ sơ thất bại");
      } finally {
        setLoadingProfile(false);
      }
    };

    fetchProfile();
  }, []);

  const handleProfileChange = (
    key: keyof UpdateProfilePayload,
    value: string,
  ) => {
    setProfileForm((prev) => ({
      ...prev,
      [key]: value,
    }));
  };

  const handlePasswordChange = (
    key: keyof ChangePasswordPayload,
    value: string,
  ) => {
    setPasswordForm((prev) => ({
      ...prev,
      [key]: value,
    }));
  };

  const handleUploadAvatar = async (
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    const file = e.target.files?.[0];
    if (!file) return;

    try {
      setUploadingAvatar(true);

      const res = await uploadService.uploadImage(file);
      const imageUrl = res.data;

      setProfileForm((prev) => ({
        ...prev,
        avatar: imageUrl,
      }));

      toast.success("Upload avatar thành công");
    } catch (error) {
      console.error(error);
      toast.error("Upload avatar thất bại");
    } finally {
      setUploadingAvatar(false);
    }
  };

  const handleSaveProfile = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      setSavingProfile(true);
      await profileService.updateMyProfile(profileForm);
      toast.success("Cập nhật hồ sơ thành công");

      setProfile((prev) =>
        prev
          ? {
              ...prev,
              fullName: profileForm.fullName,
              email: profileForm.email,
              phone: profileForm.phone,
              avatar: profileForm.avatar,
            }
          : prev,
      );
    } catch (error) {
      console.error(error);
      toast.error("Cập nhật hồ sơ thất bại");
    } finally {
      setSavingProfile(false);
    }
  };

  const handleChangePassword = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      setChangingPassword(true);
      await profileService.changePassword(passwordForm);

      toast.success("Đổi mật khẩu thành công");
      setPasswordForm(defaultPassword);
    } catch (error: any) {
      console.error(error);
      toast.error(error?.response?.data?.detail || "Đổi mật khẩu thất bại");
    } finally {
      setChangingPassword(false);
    }
  };

  if (loadingProfile) {
    return <div className="p-6">Đang tải hồ sơ...</div>;
  }

  if (!profile) {
    return <div className="p-6">Không có dữ liệu hồ sơ</div>;
  }

  return (
    <div className="grid grid-cols-1 gap-6 xl:grid-cols-3">
      <div className="p-6 bg-white border shadow-sm rounded-2xl">
        <h2 className="mb-4 text-xl font-semibold">Thông tin tài khoản</h2>

        <div className="flex flex-col items-center">
          <img
            src={profileForm.avatar || "https://via.placeholder.com/120?text=Avatar"}
            alt="avatar"
            className="object-cover w-32 h-32 mb-4 border rounded-full"
          />

          <label className="w-full">
            <span className="block mb-2 text-sm font-medium">Chọn ảnh avatar</span>
            <input
              type="file"
              accept="image/*"
              onChange={handleUploadAvatar}
              className="w-full p-2 border rounded-xl"
            />
          </label>

          {uploadingAvatar && (
            <p className="mt-2 text-sm text-violet-600">Đang upload ảnh...</p>
          )}

          <div className="w-full mt-4 space-y-2 text-sm">
            <div>
              <span className="font-medium">Username:</span> {profile.username}
            </div>
            <div>
              <span className="font-medium">Email:</span> {profile.email}
            </div>
            <div>
              <span className="font-medium">Phone:</span> {profile.phone}
            </div>
            <div>
              <span className="font-medium">Họ tên:</span> {profile.fullName}
            </div>
          </div>
        </div>
      </div>

      <div className="p-6 bg-white border shadow-sm xl:col-span-2 rounded-2xl">
        <h2 className="mb-4 text-xl font-semibold">Cập nhật hồ sơ</h2>

        <form
          onSubmit={handleSaveProfile}
          className="grid grid-cols-1 gap-4 md:grid-cols-2"
        >
          <div>
            <label className="block mb-1 text-sm font-medium">Họ tên</label>
            <input
              value={profileForm.fullName}
              onChange={(e) => handleProfileChange("fullName", e.target.value)}
              className="w-full p-3 border rounded-xl"
              placeholder="Nhập họ tên"
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium">Email</label>
            <input
              value={profileForm.email}
              onChange={(e) => handleProfileChange("email", e.target.value)}
              className="w-full p-3 border rounded-xl"
              placeholder="Nhập email"
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium">Số điện thoại</label>
            <input
              value={profileForm.phone}
              onChange={(e) => handleProfileChange("phone", e.target.value)}
              className="w-full p-3 border rounded-xl"
              placeholder="Nhập số điện thoại"
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium">Link avatar</label>
            <input
              value={profileForm.avatar}
              onChange={(e) => handleProfileChange("avatar", e.target.value)}
              className="w-full p-3 border rounded-xl"
              placeholder="Link avatar"
            />
          </div>

          <div className="md:col-span-2">
            <Button type="submit" disabled={savingProfile}>
              {savingProfile ? "Đang lưu..." : "Lưu thông tin"}
            </Button>
          </div>
        </form>
      </div>

      <div className="p-6 bg-white border shadow-sm xl:col-span-3 rounded-2xl">
        <h2 className="mb-4 text-xl font-semibold">Đổi mật khẩu</h2>

        <form
          onSubmit={handleChangePassword}
          className="grid grid-cols-1 gap-4 md:grid-cols-3"
        >
          <div>
            <label className="block mb-1 text-sm font-medium">Mật khẩu cũ</label>
            <input
              type="password"
              value={passwordForm.oldPassword}
              onChange={(e) =>
                handlePasswordChange("oldPassword", e.target.value)
              }
              className="w-full p-3 border rounded-xl"
              placeholder="Nhập mật khẩu cũ"
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium">Mật khẩu mới</label>
            <input
              type="password"
              value={passwordForm.newPassword}
              onChange={(e) =>
                handlePasswordChange("newPassword", e.target.value)
              }
              className="w-full p-3 border rounded-xl"
              placeholder="Nhập mật khẩu mới"
            />
          </div>

          <div>
            <label className="block mb-1 text-sm font-medium">
              Xác nhận mật khẩu
            </label>
            <input
              type="password"
              value={passwordForm.confirmPassword}
              onChange={(e) =>
                handlePasswordChange("confirmPassword", e.target.value)
              }
              className="w-full p-3 border rounded-xl"
              placeholder="Nhập lại mật khẩu mới"
            />
          </div>

          <div className="md:col-span-3">
            <Button type="submit" disabled={changingPassword}>
              {changingPassword ? "Đang đổi..." : "Đổi mật khẩu"}
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}