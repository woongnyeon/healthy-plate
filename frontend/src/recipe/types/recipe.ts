export interface Recipe {
  title: string;
  content: string;
  thumbnail: string;
  author: string;
  like_count: number;
  comment_count: number;
  category: string;
  created_at: string;
}

export interface Comment {
  author: string;
  content: string;
  like_count: number;
  re_comments: ReComment[];
}

export interface ReComment {
  author: string;
  content: string;
}

export interface Ingredient {
  ingredient: string;
  kcal: number;
}

export interface RecipeDetail extends Recipe {
  coments: Comment[];
  ingredients: Ingredient[];
  created_at: string;
  tags: string[];
}
