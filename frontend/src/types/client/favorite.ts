export type ClientFavoriteBuilding = {
  favoriteId: number;
  buildingId: number;
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

export type ClientFavoriteToggleResponse = {
  buildingId: number;
  favorite: boolean;
};