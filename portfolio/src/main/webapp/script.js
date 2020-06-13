// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


/**
 * Adds a random quote to the page
 */
function addRandomQuote() {
  const quotes =
      ['"You are never too old to set another goal or to dream a new dream" - C.S. Lewis', 
      '"The fool doth think he is wise, but the wise man knows himself to be a fool." - Shakespeare', 
      '"Just keep swimming " - Dory, Finding Nemo', 
      '"It\'s kind of fun to do the impossible." - Walt Disney',
      '"Two roads diverged in a wood, and I -- I took the one less traveled by, and that has made all the difference" - Robert Frost'];

  // Pick a random quote.
  const quote = quotes[Math.floor(Math.random() * quotes.length)];

  // Add it to the page.
  const quoteContainer = document.getElementById('quote-container');
  quoteContainer.innerText = quote;
}

// fetch welcome message from server and add it to DOM
function getGreeting() {
  fetch('/data').then(response => response.text()).then((greeting) => {
    document.getElementById('greeting-container').innerText = greeting;
  });
}