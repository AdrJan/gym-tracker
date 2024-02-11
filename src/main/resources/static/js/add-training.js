var exerciseNo = 0;
var list = [];

$.get('/exercise/getExercises', {})
   .done(function(data) {
       console.log(data);
       list = data;
})
.fail(function(error) {
   console.error('Błąd:', error);
});

function validate() {
   var repInputs = document.querySelectorAll("#rep");
   var weightInputs = document.querySelectorAll("#weight");

   var flagValidated = validateInputs(repInputs) || validateInputs(weightInputs);

   return flagValidated;
}

function validateInputs(inputs) {
   let flagValidated = true;
   for(let i = 0; i < inputs.length; i++) {
       if(isNaN(inputs[i].value) || inputs[i].value <= 0) {
           flagValidated = false;
           inputs[i].setAttribute("class", "form-control w-25 border-danger");
       } else {
           inputs[i].setAttribute("class", "form-control w-25");
       }
   }
   return inputs.length > 0 && flagValidated;
}

function createRep(exerciseNo) {
   var container = document.getElementById("exercise-block-" + exerciseNo);
   var repContainer = document.createElement("DIV");
   repContainer.setAttribute("class", "d-flex flex-wrap");

   var buttonLabel = document.createElement("BUTTON");
   buttonLabel.setAttribute("class", "btn btn-light w-25");
   buttonLabel.setAttribute("disabled", "");
   buttonLabel.textContent = "Seria";

   var inputRep = document.createElement("INPUT");
   inputRep.setAttribute("id", "rep");
   inputRep.setAttribute("placeholder", "powt.");
   inputRep.setAttribute("type", "number");
   inputRep.setAttribute("min", "1");
   inputRep.setAttribute("class", "form-control w-25");
   inputRep.setAttribute("name", "trainingSessionForm[" + exerciseNo + "].reps");

   var inputWeight = document.createElement("INPUT");
   inputWeight.setAttribute("id", "weight");
   inputWeight.setAttribute("placeholder", "kg");
   inputWeight.setAttribute("type", "number");
   inputWeight.setAttribute("min", "1");
   inputWeight.setAttribute("class", "form-control w-25");
   inputWeight.setAttribute("name", "trainingSessionForm[" + exerciseNo + "].weights");

   var buttonDelete = document.createElement("BUTTON");
   buttonDelete.setAttribute("onclick", "removeElement(this)");
   buttonDelete.setAttribute("type", "button");
   buttonDelete.setAttribute("class", "btn btn-outline-danger w-25");
   buttonDelete.textContent = "x";

   repContainer.appendChild(buttonLabel);
   repContainer.appendChild(inputRep);
   repContainer.appendChild(inputWeight);
   repContainer.appendChild(buttonDelete);

   container.appendChild(repContainer);
}

function createExercise() {
      var container = document.getElementById("lil-forms");

      var exerciseContainer = document.createElement("DIV");
      exerciseContainer.setAttribute("id", "exercise-block-" + exerciseNo);
      exerciseContainer.setAttribute("class", "d-flex align-content-start col-md-3 flex-wrap mt-3");

      var selectExercise = document.createElement("SELECT");
      selectExercise.setAttribute("class", "form-control w-100");
      selectExercise.setAttribute("id", "exercise");
      selectExercise.setAttribute("name", "trainingSessionForm[" + exerciseNo + "].exerciseId");

      for (var i = 0; i < list.length; i++) {
           var option = document.createElement("OPTION");
           option.appendChild(document.createTextNode(list[i].name));
           option.setAttribute("value", list[i].id);
           selectExercise.appendChild(option);
      }

      var button = document.createElement("BUTTON");
      button.setAttribute("onclick", "createRep(" + exerciseNo + ")");
      button.setAttribute("type", "button");
      button.setAttribute("class", "btn btn-success w-75");
      button.textContent = "+";

      var buttonDelete = document.createElement("BUTTON");
      buttonDelete.setAttribute("onclick", "removeContainer(" + exerciseNo + ")");
      buttonDelete.setAttribute("type", "button");
      buttonDelete.setAttribute("class", "btn btn-danger w-25");
      buttonDelete.textContent = "x";

      exerciseContainer.appendChild(button);
      exerciseContainer.appendChild(buttonDelete);
      exerciseContainer.appendChild(selectExercise);
      container.appendChild(exerciseContainer);

      exerciseNo++;
}

function removeContainer(exerciseNo) {
   document.getElementById("exercise-block-" + exerciseNo).remove();
}

function removeElement(element) {
   element.parentElement.remove();
}
