export type ClientBuildingListItem = {
  id: number;
  name: string;
  address: string;
  district: string;
  ward: string;
  street: string;
  imageUrl: string;
  rentPrice: number;
  floorArea: number;
  rentType: string;
  status: string;
};

export type ClientBuildingDetail = {
  id: number;
  name: string;
  address: string;
  district: string;
  ward: string;
  street: string;
  imageUrl: string;
  rentPrice: number;
  floorArea: number;
  rentType: string;
  description: string;
  managerName: string;
  managerPhone: string;
  status: string;
};

export type ClientBuildingSearchFilters = {
  keyword: string;
  district: string;
  rentPriceFrom: string;
  rentPriceTo: string;
  areaFrom: string;
  areaTo: string;
  rentType: string;
  sortBy: string;
  page: number;
  limit: number;
};

export type ClientBuildingListResponse = {
  items: ClientBuildingListItem[];
  totalItems: number;
  page: number;
  limit: number;
  totalPages: number;
};