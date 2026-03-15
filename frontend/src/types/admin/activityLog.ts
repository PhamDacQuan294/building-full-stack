export type ActivityLogItem = {
  id: number;
  actorId: number;
  actorEmail: string;
  actorName: string;
  action: string;
  module: string;
  description: string;
  objectId: number;
  createdDate: string;
};

export type ActivityLogFilters = {
  actorEmail: string;
  action: string;
  module: string;
  page: number;
  limit: number;
};