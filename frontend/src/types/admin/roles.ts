export interface RoleItem {
  id: number;
  name: string;
  code: string;
}

export interface RoleResponse {
  data: RoleItem[];
  message: string;
  detail: string;
}

export interface RoleDetailResponse {
  data: RoleItem;
  message: string;
  detail: string;
}

export interface RoleFormData {
  name: string;
  code: string;
}