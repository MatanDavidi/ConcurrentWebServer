function calculateColor() {

	var numeri = /\d/;
	if (!(numeri.test(document.getElementById('text').value))) {

		var red = document.getElementById('red').value;
		var green = document.getElementById('green').value;
		var blue = document.getElementById('blue').value;

		var text = document.getElementById('text').value;

		var upperCase = text.toUpperCase();
		document.getElementById('uppercase').innerHTML = upperCase;

		var lowercase = text.toLowerCase();
		lowercase = lowercase.replace(/ /g, "");
		document.getElementById('lowercase').innerHTML = lowercase;

		document.getElementById("colored").style.color = 'rgb(' + [red, green, blue].join(',') + ')';
		document.getElementById('colored').innerHTML = text;

		document.getElementById('redInput').style.backgroundColor = 'rgb(' + [red, 0, 0].join(',') + ')';
		document.getElementById('greenInput').style.backgroundColor = 'rgb(' + [0, green, 0].join(',') + ')';
		document.getElementById('blueInput').style.backgroundColor = 'rgb(' + [0, 0, blue].join(',') + ')';

		document.getElementById('redValue').value = red;
		document.getElementById('greenValue').value = green;
		document.getElementById('blueValue').value = blue;

		document.getElementById('colorRgb').innerHTML = 'rgb(' + [red, green, blue].join(', ') + ')';

		var redHex = parseInt(red) <= 16? '0' + ((parseInt(red)).toString(16)).toUpperCase() : ((parseInt(red)).toString(16)).toUpperCase();
		var greenHex = parseInt(green) <= 16? '0' + ((parseInt(green)).toString(16)).toUpperCase() : ((parseInt(green)).toString(16)).toUpperCase();
		var blueHex = parseInt(blue) <= 16? '0' + ((parseInt(blue)).toString(16)).toUpperCase() : ((parseInt(blue)).toString(16)).toUpperCase();

		var hexValue = '#' + redHex + greenHex + blueHex;

		document.getElementById('colorHex').innerHTML = hexValue;

	} else {

		window.alert("ERRORE\nPassare solo testo letterale, senza numeri.\n(Testo passato: " + document.getElementById('text').value + ")");
		document.getElementById('text').value = "";

	}

}

function changeRange(numberValue, rangeInput) {

	rangeInput.value = numberValue;
	calculateColor();

}
