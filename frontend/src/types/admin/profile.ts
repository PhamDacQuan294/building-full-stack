export type ProfileData = {
  id: number;
  fullName: string;
  email: string;
  phone: string;
  avatar: string;
  username: string;
};

export type UpdateProfilePayload = {
  fullName: string;
  email: string;
  phone: string;
  avatar: string;
};

export type ChangePasswordPayload = {
  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
};