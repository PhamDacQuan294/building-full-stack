export const formatNumber = (value?: number) => {
  if (value === null || value === undefined) return "-";
  return new Intl.NumberFormat("vi-VN").format(value);
};

export const formatVND = (value?: number) => {
  if (value === null || value === undefined) return "-";
  return new Intl.NumberFormat("vi-VN").format(value) + " ₫";
};