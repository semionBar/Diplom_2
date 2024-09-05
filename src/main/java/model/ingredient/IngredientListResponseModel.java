package model.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientListResponseModel {

    private String success;
    private List<IngredientModel> data;

    public String getRandomBunId() {

        List<IngredientModel> bunList = new ArrayList<IngredientModel>();

        for (IngredientModel ingredientModel : data) {
            if (ingredientModel.getType().equals("bun")) {
                bunList.add(ingredientModel);
            }
        }

        return bunList.get(new Random().nextInt(bunList.size())).get_id();

    }

    public String getRandomSauceId() {
        List<IngredientModel> sauceList = new ArrayList<IngredientModel>();

        for (IngredientModel ingredientModel : data) {
            if (ingredientModel.getType().equals("sauce")) {
                sauceList.add(ingredientModel);
            }
        }

        return sauceList.get(new Random().nextInt(sauceList.size())).get_id();
    }

    public String getRandomMainId() {
        List<IngredientModel> mainList = new ArrayList<IngredientModel>();

        for (IngredientModel ingredientModel : data) {
            if (ingredientModel.getType().equals("main")) {
                mainList.add(ingredientModel);
            }
        }

        return mainList.get(new Random().nextInt(mainList.size())).get_id();
    }

}
