import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Dialog, DialogContent, DialogTrigger } from "@/components/ui/dialog";
import type { BuildingFormData } from "@/types/admin/buildings";
import {
  Building2,
  MapPinned,
  Map,
  Landmark,
  Warehouse,
  Ruler,
  Banknote,
  CircleDollarSign,
  Phone,
  User,
  Image as ImageIcon,
  // FileText,
  Compass,
  Layers3,
  Clock3,
  Car,
  Bike,
  Droplets,
  Lightbulb,
  HandCoins,
  ReceiptText,
  Paintbrush,
  Link as LinkIcon,
  MapIcon,
  StickyNote,
  Plus,
} from "lucide-react";

type BuildingFormProps = {
  initialValues?: BuildingFormData;
  onSubmit: (values: BuildingFormData) => Promise<void>;
  loading?: boolean;
  submitText?: string;
  districts: Record<string, string>;
};

const defaultValues: BuildingFormData = {
  name: "",
  district: "",
  ward: "",
  street: "",
  basement: "",
  floorArea: "",
  rentPrice: "",
  managerName: "",
  managerPhone: "",
  status: "ACTIVE",
  structure: "",
  direction: "",
  level: "",
  rentTime: "",
  serviceFee: "",
  carFee: "",
  motorbikeFee: "",
  overtimeFee: "",
  waterFee: "",
  electricityFee: "",
  deposit: "",
  payment: "",
  decorationTime: "",
  brokerageFee: "",
  note: "",
  linkOfBuilding: "",
  map: "",
};

type FieldProps = {
  label: string;
  icon: React.ReactNode;
  children: React.ReactNode;
};

function Field({ label, icon, children }: FieldProps) {
  return (
    <div className="space-y-2">
      <Label className="flex items-center gap-2 text-sm font-medium text-slate-700">
        <span className="text-violet-600">{icon}</span>
        {label}
      </Label>
      {children}
    </div>
  );
}

export default function BuildingForm({ initialValues, onSubmit, loading = false, submitText = "Lưu", districts}: BuildingFormProps) {
  const [form, setForm] = useState<BuildingFormData>(
    initialValues || defaultValues,
  );

  const handleChange = (
    key: keyof BuildingFormData,
    value: string | string[] | File | null,
  ) => {
    setForm((prev) => ({
      ...prev,
      [key]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(form);
  };

  const previewSrc = form.imageFile instanceof File ? URL.createObjectURL(form.imageFile) : form.imageUrl;

  return (
    <form onSubmit={handleSubmit} className="space-y-8">
      <div className="p-6 bg-white border shadow-sm rounded-2xl border-slate-200">
        <div className="flex items-center gap-3 mb-6">
          <div className="p-2 rounded-xl bg-violet-100 text-violet-600">
            <Building2 className="w-5 h-5" />
          </div>
          <div>
            <h2 className="text-lg font-semibold text-slate-800">
              Thông tin cơ bản
            </h2>
            <p className="text-sm text-slate-500">
              Nhập các thông tin chính của toà nhà
            </p>
          </div>
        </div>

        <div className="grid grid-cols-1 gap-5 md:grid-cols-2 xl:grid-cols-3">
          <Field label="Tên toà nhà" icon={<Building2 className="w-4 h-4" />}>
            <Input
              value={form.name ?? ""}
              onChange={(e) => handleChange("name", e.target.value)}
              placeholder="Nhập tên toà nhà"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Quận / Huyện" icon={<Landmark className="w-4 h-4" />}>
            <Select
              value={form.district}
              onValueChange={(value) => handleChange("district", value)}
            >
              <SelectTrigger className="w-full h-11 rounded-xl">
                <SelectValue placeholder="-- Chọn quận --" />
              </SelectTrigger>
              <SelectContent>
                {Object.entries(districts).map(([value, label]) => (
                  <SelectItem key={value} value={value}>
                    {label}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </Field>

          <Field label="Phường" icon={<MapPinned className="w-4 h-4" />}>
            <Input
              value={form.ward ?? ""}
              onChange={(e) => handleChange("ward", e.target.value)}
              placeholder="Nhập phường"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Đường" icon={<Map className="w-4 h-4" />}>
            <Input
              value={form.street ?? ""}
              onChange={(e) => handleChange("street", e.target.value)}
              placeholder="Nhập đường"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Số tầng hầm" icon={<Warehouse className="w-4 h-4" />}>
            <Input
              value={form.basement ?? ""}
              onChange={(e) => handleChange("basement", e.target.value)}
              placeholder="VD: 2"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Diện tích sàn" icon={<Ruler className="w-4 h-4" />}>
            <Input
              value={form.floorArea ?? ""}
              onChange={(e) => handleChange("floorArea", e.target.value)}
              placeholder="VD: 1000"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Giá thuê" icon={<Banknote className="w-4 h-4" />}>
            <Input
              value={form.rentPrice ?? ""}
              onChange={(e) => handleChange("rentPrice", e.target.value)}
              placeholder="Nhập giá thuê"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field
            label="Trạng thái"
            icon={<CircleDollarSign className="w-4 h-4" />}
          >
            <Select
              value={form.status}
              onValueChange={(value) => handleChange("status", value)}
            >
              <SelectTrigger className="w-full h-11 rounded-xl">
                <SelectValue placeholder="Chọn trạng thái" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="ACTIVE">Hoạt động</SelectItem>
                <SelectItem value="INACTIVE">Ngưng hoạt động</SelectItem>
              </SelectContent>
            </Select>
          </Field>

          <Field label="Tên quản lý" icon={<User className="w-4 h-4" />}>
            <Input
              value={form.managerName ?? ""}
              onChange={(e) => handleChange("managerName", e.target.value)}
              placeholder="Nhập tên quản lý"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field
            label="Số điện thoại quản lý"
            icon={<Phone className="w-4 h-4" />}
          >
            <Input
              value={form.managerPhone ?? ""}
              onChange={(e) => handleChange("managerPhone", e.target.value)}
              placeholder="Nhập số điện thoại"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Kết cấu" icon={<Building2 className="w-4 h-4" />}>
            <Input
              value={form.structure ?? ""}
              onChange={(e) => handleChange("structure", e.target.value)}
              placeholder="Nhập kết cấu"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Hướng" icon={<Compass className="w-4 h-4" />}>
            <Input
              value={form.direction ?? ""}
              onChange={(e) => handleChange("direction", e.target.value)}
              placeholder="VD: Đông Nam"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Hạng" icon={<Layers3 className="w-4 h-4" />}>
            <Input
              value={form.level ?? ""}
              onChange={(e) => handleChange("level", e.target.value)}
              placeholder="VD: A, B, C"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Thời gian thuê" icon={<Clock3 className="w-4 h-4" />}>
            <Input
              value={form.rentTime ?? ""}
              onChange={(e) => handleChange("rentTime", e.target.value)}
              placeholder="VD: 3 năm"
              className="h-11 rounded-xl"
            />
          </Field>
        </div>
      </div>

      <div className="p-6 bg-white border shadow-sm rounded-2xl border-slate-200">
        <div className="flex items-center gap-3 mb-6">
          <div className="p-2 rounded-xl bg-violet-100 text-violet-600">
            <ReceiptText className="w-5 h-5" />
          </div>
          <div>
            <h2 className="text-lg font-semibold text-slate-800">
              Chi phí và điều khoản
            </h2>
            <p className="text-sm text-slate-500">
              Nhập các khoản phí, đặt cọc và điều khoản liên quan
            </p>
          </div>
        </div>

        <div className="grid grid-cols-1 gap-5 md:grid-cols-2 xl:grid-cols-3">
          <Field label="Phí dịch vụ" icon={<ReceiptText className="w-4 h-4" />}>
            <Input
              value={form.serviceFee ?? ""}
              onChange={(e) => handleChange("serviceFee", e.target.value)}
              placeholder="Nhập phí dịch vụ"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Phí ô tô" icon={<Car className="w-4 h-4" />}>
            <Input
              value={form.carFee ?? ""}
              onChange={(e) => handleChange("carFee", e.target.value)}
              placeholder="Nhập phí ô tô"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Phí xe máy" icon={<Bike className="w-4 h-4" />}>
            <Input
              value={form.motorbikeFee ?? ""}
              onChange={(e) => handleChange("motorbikeFee", e.target.value)}
              placeholder="Nhập phí xe máy"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Phí ngoài giờ" icon={<Clock3 className="w-4 h-4" />}>
            <Input
              value={form.overtimeFee ?? ""}
              onChange={(e) => handleChange("overtimeFee", e.target.value)}
              placeholder="Nhập phí ngoài giờ"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Phí nước" icon={<Droplets className="w-4 h-4" />}>
            <Input
              value={form.waterFee ?? ""}
              onChange={(e) => handleChange("waterFee", e.target.value)}
              placeholder="Nhập phí nước"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Phí điện" icon={<Lightbulb className="w-4 h-4" />}>
            <Input
              value={form.electricityFee ?? ""}
              onChange={(e) => handleChange("electricityFee", e.target.value)}
              placeholder="Nhập phí điện"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Tiền cọc" icon={<HandCoins className="w-4 h-4" />}>
            <Input
              value={form.deposit ?? ""}
              onChange={(e) => handleChange("deposit", e.target.value)}
              placeholder="Nhập tiền cọc"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Thanh toán" icon={<ReceiptText className="w-4 h-4" />}>
            <Input
              value={form.payment ?? ""}
              onChange={(e) => handleChange("payment", e.target.value)}
              placeholder="Nhập điều khoản thanh toán"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field
            label="Thời gian trang trí"
            icon={<Paintbrush className="w-4 h-4" />}
          >
            <Input
              value={form.decorationTime ?? ""}
              onChange={(e) => handleChange("decorationTime", e.target.value)}
              placeholder="Nhập thời gian trang trí"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field
            label="Phí môi giới"
            icon={<CircleDollarSign className="w-4 h-4" />}
          >
            <Input
              value={form.brokerageFee ?? ""}
              onChange={(e) => handleChange("brokerageFee", e.target.value)}
              placeholder="Nhập phí môi giới"
              className="h-11 rounded-xl"
            />
          </Field>
        </div>
      </div>

      <div className="p-6 bg-white border shadow-sm rounded-2xl border-slate-200">
        <div className="flex items-center gap-3 mb-6">
          <div className="p-2 rounded-xl bg-violet-100 text-violet-600">
            <LinkIcon className="w-5 h-5" />
          </div>
          <div>
            <h2 className="text-lg font-semibold text-slate-800">
              Thông tin bổ sung
            </h2>
            <p className="text-sm text-slate-500">
              Bản đồ, liên kết và mô tả thêm về toà nhà
            </p>
          </div>
        </div>

        <div className="grid grid-cols-1 gap-5 md:grid-cols-2">
          <Field label="Link toà nhà" icon={<LinkIcon className="w-4 h-4" />}>
            <Input
              value={form.linkOfBuilding ?? ""}
              onChange={(e) => handleChange("linkOfBuilding", e.target.value)}
              placeholder="Dán link chi tiết toà nhà"
              className="h-11 rounded-xl"
            />
          </Field>

          <Field label="Bản đồ" icon={<MapIcon className="w-4 h-4" />}>
            <Input
              value={form.map ?? ""}
              onChange={(e) => handleChange("map", e.target.value)}
              placeholder="Dán link bản đồ"
              className="h-11 rounded-xl"
            />
          </Field>

          <div className="md:col-span-2">
            <Field label="Ghi chú" icon={<StickyNote className="w-4 h-4" />}>
              <textarea
                value={form.note ?? ""}
                onChange={(e) => handleChange("note", e.target.value)}
                placeholder="Nhập ghi chú thêm về toà nhà"
                className="min-h-[120px] w-full rounded-xl border border-input bg-background px-3 py-3 text-sm outline-none ring-offset-background placeholder:text-muted-foreground focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
              />
            </Field>
          </div>
        </div>
      </div>

      <div className="p-6 bg-white border shadow-sm rounded-2xl border-slate-200">
        <div className="flex items-center gap-3 mb-6">
          <div className="p-2 rounded-xl bg-violet-100 text-violet-600">
            <ImageIcon className="w-5 h-5" />
          </div>
          <div>
            <h2 className="text-lg font-semibold text-slate-800">
              Ảnh toà nhà
            </h2>
            <p className="text-sm text-slate-500">
              Tải ảnh đại diện cho toà nhà
            </p>
          </div>
        </div>

        <div className="space-y-3">
          <input
            id="building-image"
            type="file"
            accept="image/*"
            className="hidden"
            onChange={(e) =>
              handleChange("imageFile", e.target.files?.[0] || null)
            }
          />

          {!previewSrc ? (
            <label
              htmlFor="building-image"
              className="flex flex-col items-center justify-center w-64 h-40 transition border border-dashed cursor-pointer rounded-2xl border-slate-300 bg-slate-50 hover:bg-slate-100"
            >
              <Plus className="w-8 h-8 text-slate-500" />
              <span className="text-sm text-slate-500">Thêm ảnh</span>
            </label>
          ) : (
            <div className="relative w-fit">
              <Dialog>
                <DialogTrigger asChild>
                  <img
                    src={previewSrc}
                    alt="Preview"
                    className="object-cover w-64 h-40 border cursor-pointer rounded-2xl"
                  />
                </DialogTrigger>

                <DialogContent className="max-w-4xl p-0 bg-transparent border-none">
                  <img
                    src={previewSrc}
                    alt="Full"
                    className="max-h-[80vh] w-full rounded-xl object-contain"
                  />
                </DialogContent>
              </Dialog>

              <button
                type="button"
                onClick={() => {
                  handleChange("imageFile", null);
                  handleChange("imageUrl", "");
                }}
                className="absolute flex items-center justify-center text-white bg-red-500 rounded-full top-2 right-2 h-7 w-7 hover:bg-red-600"
              >
                ✕
              </button>
            </div>
          )}
        </div>
      </div>

      <div className="flex items-center gap-3">
        <Button
          type="submit"
          disabled={loading}
          className="px-6 h-11 rounded-xl bg-violet-600 hover:bg-violet-700"
        >
          {submitText}
        </Button>
      </div>
    </form>
  );
}
