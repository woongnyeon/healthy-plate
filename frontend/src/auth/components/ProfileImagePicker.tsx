interface ProfileImageProps {
  previewUrl: string | null;
  defaultSrc: string;
  onPick: () => void;
  fileRef: React.RefObject<HTMLInputElement | null>;
  onFileChange: (e: React.ChangeEvent<HTMLInputElement>, autoUpload?: boolean) => void;
  isUploading?: boolean;
}

export const ProfileImagePicker = ({
  previewUrl,
  defaultSrc,
  onPick,
  fileRef,
  onFileChange,
  isUploading,
}: ProfileImageProps) => {
  return (
    <div className="flex flex-col items-start">
      <div className="relative w-[240px] h-[240px] rounded-full overflow-hidden shadow-lg bg-gray-100">
        <img
          src={previewUrl ?? defaultSrc}
          alt="profile"
          className={`w-full h-full object-cover transition-opacity ${isUploading ? 'opacity-50' : 'opacity-100'}`}
        />
        {isUploading && (
           <div className="absolute inset-0 flex items-center justify-center bg-black/10">
              <svg className="animate-spin h-8 w-8 text-white" viewBox="0 0 24 24">
                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" />
                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
              </svg>
           </div>
        )}
      </div>

      <div className="mt-6 w-[240px]">
        <button
          type="button"
          onClick={onPick}
          disabled={isUploading}
          className="w-full rounded-full border border-gray-200 py-3 text-sm font-medium hover:bg-gray-50 transition disabled:opacity-50 disabled:cursor-not-allowed"
        >
          📷 사진 변경
        </button>

        <input
          ref={fileRef}
          type="file"
          accept="image/png,image/jpeg"
          className="hidden"
          onChange={(e) => onFileChange(e, true)}
        />

        <p className="mt-3 text-xs text-gray-400 text-center">
          JPG, PNG 파일만 가능합니다.
          <br />
          (최대 5MB)
        </p>
      </div>
    </div>
  );
};
