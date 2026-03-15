export type CustomerItem = {
  id: number;
  fullName: string;
  phone: string;
  email: string;
  companyName: string;
  demand: string;
  status: string;
  note: string;
};

export type CustomerFormData = {
  fullName: string;
  phone: string;
  email: string;
  companyName: string;
  demand: string;
  status: string;
  note: string;
};

export type CustomerFilters = {
  fullName: string;
  phone: string;
  email: string;
  staffId: string;
  status: string;
  page: number;
  limit: number;
};