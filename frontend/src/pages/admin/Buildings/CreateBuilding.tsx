import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import BuildingForm from "./BuildingForm";
import { buildingService } from "@/services/admin/buildingService";
import type { BuildingFormData } from "@/types/admin/buildings";

export default function CreateBuilding() {
  const [loading, setLoading] = useState(false);
  const [districts, setDistricts] = useState<Record<string, string>>({});
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMeta = async () => {
      try {
        const res = await buildingService.getBuildingMeta();
        setDistricts(res.data.districts || {});
      } catch (error) {
        console.error(error);
        toast.error("Không tải được danh sách quận!");
      }
    };

    fetchMeta();
  }, []);

  const handleCreate = async (values: BuildingFormData) => {
    try {
      setLoading(true);

      let imageUrl = "";

      console.log(values.imageFile);
      
      if (values.imageFile) {
        const uploadRes = await buildingService.uploadBuildingImage(values.imageFile);
        imageUrl = uploadRes.data;
      }

      await buildingService.createBuilding({
        ...values,
        imageUrl,
      });

      toast.success("Tạo toà nhà thành công!");
      navigate("/admin/buildings");
    } catch (error) {
      console.error(error);
      toast.error("Tạo toà nhà thất bại!");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 border rounded-xl border-border bg-background">
      <h1 className="mb-6 text-xl font-semibold">Thêm mới toà nhà</h1>
      <BuildingForm
        onSubmit={handleCreate}
        loading={loading}
        submitText="Thêm mới"
        districts={districts}
      />
    </div>
  );
}
