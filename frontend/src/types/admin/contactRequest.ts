export type AdminContactRequestItem = {
  id: number;
  fullName: string;
  phone: string;
  email: string;
  status: string;
  buildingId?: number;
  buildingName?: string;
  createdDate?: string;
};