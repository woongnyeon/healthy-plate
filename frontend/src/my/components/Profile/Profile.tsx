import { useGetUserInfo } from "../../../auth/hooks/useAuthQuery";
import { useChangeProfileImage } from "../../hooks/useChangeProfileImage";
import { ChangeProfileImagePicker } from "./ChangeProfileImagePicker";

export const Profile = () => {
  const user = useGetUserInfo();
  const {
    pick,
    fileRef,
    onChange: onFileChange,
    isUploading,
    previewUrl,
    uploadError,
    uploadedUrl,
  } = useChangeProfileImage();

  return (
    <section className="w-full flex justify-between items-center px-8 py-10">
      <div className="flex items-center gap-6">
        <div className="relative">
          <ChangeProfileImagePicker
            previewUrl={previewUrl}
            defaultSrc={user.data?.profile.profileImageUrl || ""}
            onPick={pick}
            fileRef={fileRef}
            onFileChange={onFileChange}
            isUploading={isUploading}
          />
        </div>
        <div>
          <div className="flex flex-col gap-2">
            <div className="flex gap-1">
              <h2 className="text-primary text-card-title font-semibold">
                {user?.data?.profile.nickname}
              </h2>
              <span className="text-profile text-profile-items pt-1">
                (heisfxxkingay)
              </span>
            </div>
            <p className="mt-1 text-profile text-profile-items text-sm">
              {user?.data?.profile.introduction}
            </p>
          </div>
        </div>
      </div>

      <div className="flex gap-10 text-center">
        <div>
          <p className="text-lg font-semibold">1,240</p>
          <p className="text-sm text-gray-500">팔로워</p>
        </div>
        <div>
          <p className="text-lg font-semibold">45</p>
          <p className="text-sm text-gray-500">팔로잉</p>
        </div>
        <div>
          <p className="text-lg font-semibold">156</p>
          <p className="text-sm text-gray-500">레시피</p>
        </div>
      </div>
    </section>
  );
};
