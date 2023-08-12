package com.kan.gemstone.ioc;

public class Chef {

    // 식재료 공장 위치
    private  IngredientFactory ingredientFactory;

    // 셰프가 식재료 공장과 협업하기 위한 DI
    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    public String cook(String menu) {
        // 요리 재료 준비
        Ingredient ingredient = ingredientFactory.get(menu);

        // 요리 반환
        return ingredient.getName() + "으로 만든 " + menu;
    }
}
