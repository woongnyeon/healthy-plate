import { useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";

import { useAuth } from "../hooks/useAuth";

import { ProfileImagePicker } from "../components/ProfileImagePicker";
import { NicknameField } from "../components/NicknameField";
import { IntroTextarea } from "../components/IntroTextArea";
import { RegisterFooter } from "../components/RegisterFooter";

import { useProfileImage } from "../hooks/useProfileImage";
import { validateNickname, validateDescription } from "../utills/validators";
import { useNicknameCheck } from "../hooks/useNicknameCheck";

export const RegisterProfilePage = () => {
  const navigate = useNavigate();
  const { handeSignup } = useAuth();

  const [nickname, setNickname] = useState("");
  const [desc, setDesc] = useState(
    "자신만의 요리 철학이나 좋아하는 스타일을 자유롭게 적어주세요."
  );

  const nicknameValidation = useMemo(
    () => validateNickname(nickname),
    [nickname]
  );
  const descValidation = useMemo(() => validateDescription(desc), [desc]);

  const { checking, isAvailable, message, onCheck, canSubmit } =
    useNicknameCheck(nickname);

  const onNicknameChange = (v: string) => {
    setNickname(v);
  };

  const {
    fileRef,
    previewUrl,
    pick,
    onChange: onFileChange,
  } = useProfileImage();

  const submit = async () => {
    // 1) 기본 유효성
    if (!nicknameValidation.ok) {
      alert(nicknameValidation.message);
      return;
    }
    if (!descValidation.ok) {
      alert(descValidation.message);
      return;
    }

    // 2) 중복확인 강제
    if (!canSubmit) {
      alert("닉네임 중복 확인을 완료해주세요.");
      return;
    }

    // ✅ 가입 요청
    handeSignup({
      nickname: nicknameValidation.value,
      introduction: descValidation?.value, // 필요하면 value 사용
      // profileImage: file,               // 필요하면 추가
    });
  };

  return (
    <div className="mt-20 min-h-screen bg-bg px-8 py-10">
      <div className="max-w-[1200px] mx-auto">
        <div className="mb-10">
          <h1 className="text-4xl font-extrabold leading-tight">
            만나서 반가워요!
            <br />
            셰프님을 소개해 주세요
          </h1>
          <p className="mt-3 text-gray-500">
            멋진 프로필 사진과 소개로 나만의 키친을 꾸며보세요.
          </p>
        </div>

        <div className="grid grid-cols-12 gap-10 items-start">
          <div className="col-span-4">
            <ProfileImagePicker
              previewUrl={previewUrl}
              defaultSrc="/images/default-profile.png"
              onPick={pick}
              fileRef={fileRef}
              onFileChange={onFileChange}
            />
          </div>

          <div className="col-span-8">
            <NicknameField
              value={nickname}
              onChange={onNicknameChange}
              checking={checking}
              isAvailable={isAvailable}
              message={message}
              onCheck={() => {
                // ✅ 유효성 통과한 경우에만 중복확인 호출
                if (!nicknameValidation.ok) return;
                onCheck();
              }}
              error={
                !nicknameValidation.ok && nickname.length > 0
                  ? nicknameValidation.message
                  : ""
              }
            />

            <IntroTextarea
              value={desc}
              onChange={setDesc}
              error={!descValidation.ok ? descValidation.message : ""}
            />

            <RegisterFooter
              onBack={() => navigate(-1)}
              onSubmit={submit}
              submitDisabled={
                !canSubmit || !descValidation.ok || !nicknameValidation.ok
              }
            />
          </div>
        </div>
      </div>
    </div>
  );
};
