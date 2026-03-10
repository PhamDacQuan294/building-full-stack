import api from "@/lib/axios";
import type { AssignBuildingRequest, BuildingFilters, BuildingResponse } from "@/types/admin/buildings";

type QueryParams = Record<string, string | number | boolean | string[]>;

const isStringArray = (v: unknown): v is string[] =>
  Array.isArray(v) && v.every((x) => typeof x === "string");

function buildParams(filters: BuildingFilters): QueryParams {
  const selectedTypes = Object.keys(filters.types).filter((k) => filters.types[k]);

  const raw: Record<string, unknown> = {
    name: filters.name?.trim(),
    street: filters.street?.trim(),
    district: filters.district,
    ward: filters.ward,
    floorArea: filters.floorArea,
    basement: filters.basement,
    rentFrom: filters.rentFrom,
    rentTo: filters.rentTo,
    staffId: filters.staffId,

    types: selectedTypes.length ? selectedTypes : undefined,

    status: filters.status !== "all" ? filters.status : undefined,
    sort: filters.sort,
    page: filters.page,
    limit: filters.limit,
  };

  const params: QueryParams = {};

  for (const [key, value] of Object.entries(raw)) {
    if (value === null || value === undefined) continue;
    if (typeof value === "string" && value.trim() === "") continue;

    if (typeof value === "string" || typeof value === "number" || typeof value === "boolean") {
      params[key] = value;
      continue;
    }

    if (isStringArray(value)) {
      params[key] = value;
      continue;
    }
  }
  return params;
}

export const buildingService = {
  search: async (filters: BuildingFilters): Promise<BuildingResponse> => {
    const res = await api.get("/buildings", {
      params: buildParams(filters),
    });
    return res.data;
  },

  changeStatus: async (id: string | number, status: string) => {
    const res = await api.patch(`/buildings/change-status/${id}/${status}`);
    return res.data;
  },

  changeMultiStatus: async (ids: Array<string | number>, status: string) => {
    const res = await api.patch("/buildings/change-multi", {
      ids,
      status,
    })
    return res.data
  },

  loadStaffs: async (buildingId: string | number) => {
    const res = await api.get(`/buildings/${buildingId}/staffs`)
    return res.data
  },

  assignBuilding: async (payload: AssignBuildingRequest) => {
    const res = await api.post("/buildings/assignment", payload)
    return res.data
  },
};