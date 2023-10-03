//------------------calculation.js------------------//
let calculationURL = new URL('http://localhost:8080/api/calculate');
// let calculationURL = new URL('https://seatimecalculator.ru/api/calculate');

export {calculationURL};

//------------------contactForm.js------------------//

let contactUrl = new URL('http://localhost:8080/api/contact');
// let contactUrl = new URL('https://seatimecalculator.ru/api/contact');

export {contactUrl};


//------------------forgotPassword.js------------------//

let resetPasswordURL = new URL('http://localhost:8080/api/resetPassword');
// let resetPasswordURL = new URL('https://seatimecalculator.ru/api/resetPassword');

export {resetPasswordURL};

//------------------registration.js------------------//

let validationURL = new URL('http://localhost:8080/api/emailValidation');
// let validationURL = new URL('https://seatimecalculator.ru/api/emailValidation');
let forgotPasswordLink = 'http://localhost:8080/forgot_password';
// let forgotPasswordLink = 'https://seatimecalculator.ru/forgot_password';

export {validationURL, forgotPasswordLink};

import {particles} from "/static/js/visuals/particles.js";
import {burgerMenu} from "/static/js/burger.js";

particles();
burgerMenu();