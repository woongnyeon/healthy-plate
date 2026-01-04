import { EditToolBar } from "./Toolbar/EditToolBar";
import { TitleEditor } from "./Content/TitleEditor";
import { TagEditor } from "./Tag/TagEditor";
import { IngredientEditor } from "./Ingredinet/IngredientEditor";
import { RecipeEditor } from "./Content/RecipeEditor";
import { EditActionBar } from "./Content/EditActionBar";
import { useEdit } from "../../hooks/useEdit";

export const EditSection = () => {
  const { editor, titleProps, tagProps, ingredientProps, actions } = useEdit();

  if (!editor) return null;

  return (
    <section className="min-w-0">
      <div className="sticky top-0 z-20 bg-white pt-2">
        <div className="flex justify-center">
          <EditToolBar editor={editor} />
        </div>
        <div className="mt-4 border-t border-gray-100" />
      </div>

      <TitleEditor {...titleProps} />

      <TagEditor {...tagProps} />

      <IngredientEditor {...ingredientProps} />

      <div className="mt-10 w-full max-w-[720px]">
        <div className="max-h-[60vh] overflow-y-auto">
          <RecipeEditor editor={editor} />
        </div>
      </div>

      <EditActionBar
        onSaveDraft={actions.onSaveDraft}
        onSubmit={actions.onSubmit}
        submitDisabled={actions.submitDisabled}
      />
    </section>
  );
};
