registrationApp.controller('registrationController',['$scope','registrationService',function($scope, registrationService){
	$scope.student={firstName:'', lastName:'', age:'', id:''};
	$scope.students=null;
	$scope.error = null;
	
	$scope.resetForm = function(){
		$scope.student = {firstName:'', lastName:'', age:'', id:''};
		$scope.studentForm.$setPristine();
		$scope.studentForm.$setUntouched();		
	}
	$scope.createStudent = function(){		
		if($scope.student.id != null && $scope.student.id != ""){
			$scope.updateStudent();	
		}else{
			registrationService.createStudent($scope.student).then(function(response){
				console.log(response);
				if(response.data !== undefined){
					$scope.error = response.data.message;
				}else{
					$scope.getStudents();
					$scope.resetForm();
				}
				
			});
		}
	}
	
	$scope.getStudents = function(){
		registrationService.getStudents().then(function(response){
			if(response.data !== undefined){
				$scope.error = response.data.message;
			}else{
				$scope.students = response;
			}
		});
	}
	
	$scope.getStudents();

	$scope.updateStudent = function(){		
		registrationService.updateStudent($scope.student).then(function(response){
			if(response.data !== undefined){
				$scope.error = response.data.message;
			}else{
				$scope.student = response;
				$scope.getStudents();
				$scope.resetForm();
			}
		});
	}
	
	$scope.deleteStudent = function(student){		
		registrationService.deleteStudent(student).then(function(response){
			if(response.data !== undefined){
				$scope.error = response.data.message;
			}else{
				$scope.getStudents();
			}

		});
	}
	
	$scope.setupFormForUpdate = function(student){
		angular.copy(student, $scope.student);
	}

}]);