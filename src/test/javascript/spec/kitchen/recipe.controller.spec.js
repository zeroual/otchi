

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
    describe('add new recipe', function () {
         it('should ask the server to save the new recipe', function () {
             scope.recipe={description:'toto'};
             httpBackend.expectPOST('/rest/v1/recipe',scope.recipe).respond(201);
             scope.addRecipe();
             httpBackend.flush();
        });

        it('should add the current recipe to the list', function () {
            var newRecipe={ description:'toto2' };
            scope.recipe=newRecipe;
            httpBackend.expectPOST("/rest/v1/recipe",scope.recipe).respond(201);
            scope.addRecipe();
            httpBackend.flush();
            var newRecipesList = [{description:'toto1'},{description:'toto2'}]
            expect(JSON.stringify(scope.recipesList)).toEqual(JSON.stringify(newRecipesList));
        });

    });
});