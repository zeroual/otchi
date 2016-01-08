

describe('Recipe Controller', function () {

    var scope;
    var httpBackend;
    var oldRecipes=[ {description:'toto1'}];

    beforeEach(module('kitchen'));

    beforeEach(inject(function ($controller,$rootScope,$httpBackend) {
        scope = $rootScope.$new();
        httpBackend=$httpBackend;
        httpBackend.expectGET('/rest/v1/recipe').respond(oldRecipes);
        $controller('RecipeController',{$scope:scope});
        httpBackend.flush();
    }));

    //FIXME Remove JSON.stringify
    it('should init the recipe list by the data fetched on the server', function () {
        expect(JSON.stringify(scope.recipesList)).toEqual(JSON.stringify(oldRecipes));
    });

    describe('on load', function () {
        it('should init the recipe', function () {
            expect(scope.recipe).toBeDefined();
            expect(scope.recipe.ingredients.length).toEqual(0);
            expect(scope.recipe.instructions.length).toEqual(0);

        });
    });
    describe('add new recipe', function () {
         it('should ask the server to save the new recipe', function () {
             scope.recipe={description:'toto'};
             httpBackend.expectPOST('/rest/v1/recipe',scope.recipe).respond(201);
             scope.createRecipe();
             httpBackend.flush();
        });

        it('should add the current recipe to the list', function () {
            var newRecipe={ description:'toto2' };
            scope.recipe=newRecipe;
            httpBackend.expectPOST("/rest/v1/recipe",scope.recipe).respond(201);
            scope.createRecipe();
            httpBackend.flush();
            var newRecipesList = [{description:'toto1'},{description:'toto2'}]
            expect(JSON.stringify(scope.recipesList)).toEqual(JSON.stringify(newRecipesList));
        });

        describe("add ingredient", function () {
            it('should add new ingredient to recipe', function () {
                scope.addIngredient();
                expect(scope.recipe.ingredients.length).toEqual(1);
            });
        });

        describe("remvoe ingredient", function () {
            it('should remove ingredient form recipe', function () {
                scope.recipe.ingredients = [
                    {data:'data1'},{data:'data2'}
                ];
                scope.removeIngredient(1);
                expect(scope.recipe.ingredients).toEqual([{data:'data1'}]);

            });
        });

        describe("add instruction", function () {
            it('should add new instruction to reciope', function () {
                scope.addInstruction();
                expect(scope.recipe.instructions.length).toEqual(1);
            });
        });

        describe("remvoe instuction", function () {
            it('should remove instruction form recipe', function () {
                scope.recipe.instructions = [
                    {data:'data1'},{data:'data2'}
                ];
                scope.removeInstruction(1);
                expect(scope.recipe.instructions).toEqual([{data:'data1'}]);

            });
        });

    });
});