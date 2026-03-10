export type BuildingStatus = "all" | "ACTIVE" | "INACTIVE";

export interface BuildingFilters {
  name: string;
  floorArea: string;
  district: string;
  ward: string;
  street: string;
  basement: string;
  rentFrom: string;
  rentTo: string;
  staffId: string;

  types: Record<string, boolean>;

  status: BuildingStatus;
  sort: string;
  page: number;
  limit: number;
}

export interface BuildingItem {
  id: string | number;
  name: string;
  address?: string;
  floorArea?: number;
  rentPrice?: number;
  status?: string;
}

export type IdNameMap = Record<string, string>;

export interface BuildingMeta {
  staffs: IdNameMap;     // { "2": "Staff One" }
  districts: IdNameMap;  // { "QUAN_1": "Quận 1" } (ví dụ)
  rentTypes: IdNameMap;  // { "WHOLE": "Nguyên căn" } (ví dụ)
}

export interface BuildingResponseData extends BuildingMeta {
  buildings: BuildingItem[];
}

export interface BuildingResponse {
  data: BuildingResponseData;
  detail: string;
  message: string;
}

export interface AssignBuildingRequest {
  buildingId: number
  staffs: number[]
}