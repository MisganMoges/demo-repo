// build a javaScript Object
var ourDog = {
    "name": "Campaer",
    "legs": 4,
    "tails":1,
    "friends": ["everything!"]

};

//only change the code below this line
var myDog = {
    "name": "Quincy",
    "legs": 3,
    "tails": 2,
    "friends": []

}

//accessing the properties of javaScript object
var testObject = {
    "hat": "ballcap",
    "shirt": "jersey",
    "shoes": "cleats"
};
testObject.hat = "sphericalcap";
testObject.bark = "bow- wow";
var hatValue = testObject["hat"];
var shirtValue = testObject["shoes"];
console.log(hatValue);
console.log(shirtValue);
console.log(testObject(["bark"]));

var testObj = {
    12: "Namath",
    16: "MOntana",
    19: "Unitas"
};
var playerNumber = 16;
var player = testObj[playerNumber];
console.log(player);






















