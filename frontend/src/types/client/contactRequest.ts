export type ClientContactRequestPayload = {
  fullName: string;
  phone: string;
  email: string;
  message: string;
  buildingId?: number;
};

export type ClientContactRequestResponse = {
  id: number;
  fullName: string;
  phone: string;
  email: string;
  message: string;
  status: string;
  buildingId?: number;
  buildingName?: string;
};