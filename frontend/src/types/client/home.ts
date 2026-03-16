export type ClientBuildingCard = {
  id: number;
  name: string;
  district: string;
  ward: string;
  street: string;
  address: string;
  imageUrl: string;
  rentPrice: number;
  floorArea: number;
  status: string;
};

export type HighlightDistrict = {
  code: string;
  name: string;
  totalBuildings: number;
};

export type HomePageData = {
  featuredBuildings: ClientBuildingCard[];
  newestBuildings: ClientBuildingCard[];
  highlightDistricts: HighlightDistrict[];
  districts: string[];
  bannerTitle: string;
  bannerDescription: string;
};

export type HomeSearchFilters = {
  keyword: string;
  district: string;
  rentPriceFrom: string;
  rentPriceTo: string;
  page: number;
  limit: number;
};