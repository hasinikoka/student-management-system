registrationApp.service('registrationService', function($resource){
	var Student = $resource('/StudentRegistration/students',{},
		{ 'get':    {method:'GET'},
		  'save':   {method:'POST'},
		  'update': {method:'PUT'},
		  'query':  {method:'GET', isArray:true},
		  'remove': {method:'DELETE', params: {id: 'id'}}
		}
	);

	this.createStudent = function(studentPassed){
		return Student.save(studentPassed).$promise.then(function(student) {
	    	return student;
	    },function(error) {
	    	return error;
       });
	}
	
	this.updateStudent = function(studentPassed){
		return Student.update(studentPassed).$promise.then(function(student) {
	    	return student;
	    },function(error) {
	    	return error;
      });
	}
	
	this.getStudents = function(){
		return Student.query().$promise.then(function(students) {
	    	return students;
	    },function(error) {
           return error;
	    });
	}

	this.deleteStudent = function(studentPassed){
		return Student.remove({id:studentPassed.id}).$promise.then(function(data) {
	    	return data;
	    },function(error) {
            return error;
	    });
	}

});