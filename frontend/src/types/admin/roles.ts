export interface PermissionItem {
  id: number;
  code: string;
  name: string;
  description?: string;
}

export interface RoleItem {
  id: number;
  code: string;
  name: string;
  description?: string;
  permissions: PermissionItem[];
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

export interface PermissionResponse {
  data: PermissionItem[];
  message: string;
  detail: string;
}

export interface RoleFormData {
  code: string;
  name: string;
  description: string;
  permissionIds: number[];
}