describe('Recipe publisher directive', function () {

    var $scope;
    var $httpBackend;
    beforeEach(module('publisher'));
    beforeEach(module('directives.templates'));
    var $rootScope;
    var ToasterService;
    var $state;

    beforeEach(inject(function (_$rootScope_, $compile, _$httpBackend_, _ToasterService_, _$state_) {
        $httpBackend = _$httpBackend_;
        $rootScope = _$rootScope_;
        ToasterService = _ToasterService_;
        $state = _$state_;
        $scope = $rootScope.$new();
        var element = angular.element('<recipe-publisher/>');
        $compile(element)($scope);
        $scope.$digest();
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
    });

    describe('publish the recipe as post', function () {
        beforeEach(function () {
            spyOn($state, 'go');
        });

        it('should ask the server to save the new recipe', function () {
            $scope.recipe = {description: 'toto'};
            $httpBackend.expectPOST('/rest/v1/post/recipe').respond(201, {id: 2});
            $scope.shareRecipe();
            $httpBackend.flush();
        });

        it('should notify the feed loader that a new post is published', function () {
            spyOn($rootScope, '$broadcast').and.returnValue({preventDefault: true});
            $scope.recipe = {description: 'toto'};
            var newPost = {id: 12};
            $httpBackend.expectPOST('/rest/v1/post/recipe').respond(newPost);
            $scope.shareRecipe();
            $httpBackend.flush();

            jasmine.addCustomEqualityTester(angular.equals);
            expect($scope.$broadcast).toHaveBeenCalledWith("NEW_POST_PUBLISHED_EVENT", newPost);
        });

        it('should reset the old recipe after sharing', function () {
            $scope.recipe = {description: 'toto'};
            $httpBackend.expectPOST('/rest/v1/post/recipe').respond(201, {id: 2});
            $scope.shareRecipe();
            $httpBackend.flush();
            expect($scope.recipe).toBeDefined();
            expect($scope.recipe.ingredients.length).toEqual(0);
            expect($scope.recipe.instructions.length).toEqual(0);
        });

        it('should redirect user to view the new recipe if is posting is succeeded', function () {
            $scope.recipe = {description: 'toto'};
            $httpBackend.expectPOST('/rest/v1/post/recipe').respond(201, {id: 52});
            $scope.shareRecipe();
            $httpBackend.flush();
            var showRecipeState = 'showRecipe';
            expect($state.go).toHaveBeenCalledWith(showRecipeState, {feedId: 52});
        });

        it('should notify user when recipe posting is failed', function () {
            spyOn(ToasterService, 'error');
            $scope.recipe = {description: 'toto'};
            $httpBackend.expectPOST('/rest/v1/post/recipe').respond(501);
            $scope.shareRecipe();
            $httpBackend.flush();
            expect(ToasterService.error).toHaveBeenCalledWith('post.recipe.failed');
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

    describe('images uploader', function () {

        it('should have empty image on load', function () {
            expect($scope.recipe.pictures).toEqual([]);
        });

        it('should remove selected image', function () {
            var image1 = {name: "image1"};
            var image2 = {name: "image2"};
            $scope.recipe.pictures = [image1, image2];
            $scope.deleteImage(image1);
            expect($scope.recipe.pictures).toEqual([image2]);
        });
    });

});