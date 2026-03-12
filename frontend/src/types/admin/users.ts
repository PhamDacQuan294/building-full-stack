export interface UserItem {
  id: number;
  fullName: string;
  email: string;
  phone: string;
  status: string;
  roles: string[];
}

export interface UserResponse {
  data: UserItem[];
  message: string;
  detail: string;
}

export interface CreateUserPayload {
  userName: string;
  fullName: string;
  email: string;
  phone: string;
  password: string;
  status: "ACTIVE" | "INACTIVE";
  roleIds: number[];
}