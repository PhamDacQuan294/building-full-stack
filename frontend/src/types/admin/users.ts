export interface UserItem {
  id: number;
  fullName: string;
  email: string;
  phone: string;
  username: string;
  avatar: string;
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
  avatar: string;
  status: "ACTIVE" | "INACTIVE";
  roleIds: number[];
}

export type UserDetail = {
  id: number;
  fullName: string;
  email: string;
  phone: string;
  username: string;
  avatar: string;
  status: string;
  roleIds: number[];
};

export type UserFilters = {
  fullName: string;
  email: string;
  phone: string;
  status: string;
  page: number;
  limit: number;
};


export type UpdateUserPayload = {
  fullName: string;
  email: string;
  phone: string;
  username: string;
  avatar: string;
  roleIds: number[];
};
