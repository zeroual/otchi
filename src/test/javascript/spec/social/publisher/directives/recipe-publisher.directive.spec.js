describe('Recipe publisher directive', function () {

    var $scope;
    var $httpBackend;
    beforeEach(module('publisher'));
    beforeEach(module('directives.templates'));

    beforeEach(inject(function ($rootScope, $compile, _$httpBackend_) {
        $httpBackend = _$httpBackend_;
        $scope = $rootScope.$new();
        var element = angular.element('<recipe-publisher/>');
        $compile(element)($scope);
        $scope.$digest();
        $scope = element.isolateScope() || element.scope()
    }));


    describe('on load', function () {

        beforeEach(function () {
            spyOn($scope, 'shareRecipe');
        });

        it('should init the recipe model', function () {
            expect($scope.recipe).toBeDefined();
            expect($scope.recipe.ingredients.length).toEqual(0);
            expect($scope.recipe.instructions.length).toEqual(0);

        });

        it('should wait the directive parent to send an event to share recipe', function () {
            $scope.$broadcast('SHARE_RECIPE_EVENT');
            expect($scope.shareRecipe).toHaveBeenCalled();

        });
    });

    describe('publish the recipe as post', function () {
        it('should ask the server to save the new recipe', function () {
            $scope.recipe = {description: 'toto'};
            $httpBackend.expectPOST('/rest/v1/post/recipe', $scope.recipe).respond(201);
            $scope.shareRecipe();
            $httpBackend.flush();
        });
    });

    describe("add ingredient action", function () {
        it('should add new ingredient to recipe', function () {
            $scope.addIngredient();
            expect($scope.recipe.ingredients.length).toEqual(1);
        });
    });

    describe("remove ingredient action", function () {
        it('should remove ingredient form recipe', function () {
            $scope.recipe.ingredients = [
                {data: 'data1'}, {data: 'data2'}
            ];
            $scope.removeIngredient(1);
            expect($scope.recipe.ingredients).toEqual([{data: 'data1'}]);

        });
    });

    describe("add instruction action", function () {
        it('should add new instruction to recipe', function () {
            $scope.addInstruction();
            expect($scope.recipe.instructions.length).toEqual(1);
        });
    });

    describe("remove instruction action", function () {
        it('should remove instruction form recipe', function () {
            $scope.recipe.instructions = [
                {data: 'data1'}, {data: 'data2'}
            ];
            $scope.removeInstruction(1);
            expect($scope.recipe.instructions).toEqual([{data: 'data1'}]);

        });
    });


});