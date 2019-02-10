const DEFAULT_SPHERE_WIDTH = 256;
const DEFAULT_SPHERE_HEIGHT = 256;

const picNum = 7;

var speed = 1;

var sphereWidth = DEFAULT_SPHERE_WIDTH;
var sphereHeight = DEFAULT_SPHERE_HEIGHT;

var spheres = [];

function setHeight() {
	document.body.style.height = (window.innerHeight - 16) + "px";
}

function doubleSphere() {

	$("div").remove();

	var visibleSpheres = $("img");

	for (var j = 0; j < visibleSpheres.length; j++) {

		visibleSpheres[j].style.width = "" + sphereWidth + "px";
		visibleSpheres[j].style.height = "" + sphereHeight + "px";

	}

	var i = 0;
	var sl = spheres.length;
	var maxWidth = $("body").width() - sphereWidth;
	var maxHeight = $("body").height() - sphereHeight;

	do {

		var sphere = document.createElement("img");
		var imgName = "img/" + getRndInteger(1, picNum) + ".png";
		sphere.src =  imgName;
		sphere.alt = "Ciao, sono una sfera... Il mio lavoro è girare a vuoto su questa pagina. Visto che stai vedendo questo testo, contatta il suo autore per notificargli che qualcosa non funziona.";

		sphere.style.width = "" + (sphereWidth) + "px";
		sphere.style.height = "" + (sphereHeight) + "px";
		var top = "" + getRndInteger(0, maxHeight) + "px";
		sphere.style.top = top;
		var left = "" + getRndInteger(0, maxWidth - sphereHeight) + "px";
		sphere.style.left = left;

		document.body.appendChild(sphere);
		spheres.push(sphere);

		danceTheNightAway(sphere);

		i++;

	} while (i < sl);

	sphereWidth /= 1.5;
	sphereHeight /= 1.5;

	speed /= 1.5;

}

function getRndInteger(min, max) {
    return Math.floor(Math.random() * (max - min + 1) ) + min;
}

function danceTheNightAway(dancer) {

	var goingDown, goingRight;

	switch (getRndInteger(0, 3)) {

		case 0:
			goingDown = false;
			goingRight = false;
			break;

		case 1:
			goingDown = false;
			goingRight = true;
			break;

		case 2:
			goingDown = true;
			goingRight = true;
			break;

		case 3:
			goingDown = true;
			goingRight = false;
			break;

	}

	var posX = (dancer.style.left).split("px")[0] * 1;
	var posY = (dancer.style.top).split("px")[0] * 1;
    var id = setInterval(dance, speed);
    function dance() {
        if (posX <= 0) {
			goingRight = true;
		} else if (posX >= $("body").width() - ((dancer.style.width).split("px")[0])) {
			goingRight = false;
		}

		if (posY <= 0) {
			goingDown = true;
		} else if (posY >= ($("body").height()) - ((dancer.style.height).split("px")[0])) {
			goingDown = false;
		}

		if (goingRight) {
			posX++;
		} else {
			posX--;
		}

		if (goingDown) {
			posY++;
		} else {
			posY--;
		}
        dancer.style.left = posX + 'px';
        dancer.style.top = posY + 'px';
    }

}

window.onresize = setHeight;
