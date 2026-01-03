import { EditSection } from "../components/Edit/EditSection";
import { PreviewSection } from "../components/Preview/PreviewSection";

export const EditPage = () => {
  return (
    <div className="h-screen overflow-hidden bg-white pt-16">
      <div className="mx-auto flex h-full max-w-full flex-col px-4 md:px-6 lg:px-8">
        <div className="pt-6" />

        <div className="flex-1 overflow-y-auto pb-10">
          <div className="grid grid-cols-1 lg:grid-cols-[1.2fr_1.4fr] gap-10">
            <EditSection />
            <PreviewSection />
          </div>
        </div>
      </div>
    </div>
  );
};
