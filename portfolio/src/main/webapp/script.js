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

// fetch JSON data from server
function getMessages() {
  fetch('/data').then(response => response.json()).then((comments) => {
      const messageContainer = document.getElementById('message-container');
      
      for (var i = 0; i < comments.length; i++) {
          messageContainer.appendChild(outputMessage(comments[i]));
      } 
  });
}

function loadComments() {
  fetch('/data').then(response => response.json()).then((comments) => {
      const commentListElement = document.getElementById('comment-list');
      
      comments.forEach((comment) => {
      commentListElement.appendChild(createCommentElement(comment));
    })
  });
}

/** Creates an element that represents a comment. */
function createCommentElement(comment) {
    const messageElement = document.createElement('li');
    messageElement.className = 'comment';
    const nameElement = document.createElement('span');
    nameElement.innerText = comment.name + ": ";
    const commentElement = document.createElement('p');
    commentElement.innerText = comment.comment;
    messageElement.appendChild(nameElement);
    messageElement.appendChild(commentElement);
    console.log(messageElement);
    return messageElement;
}

// Adds/outputs each message to DOM 
function outputMessage(text) {
  const messageElement = document.createElement('p');
  messageElement.innerText = text;
  return messageElement;
}

/* expand and minimize side navigation bar */
function expandNavBar() {
  document.getElementById("nav-bar").style.width = "250px";
  document.getElementById("content").style.marginLeft = "250px";
}

function minimizeNavBar() {
  document.getElementById("nav-bar").style.width = "0";
  document.getElementById("content").style.marginLeft= "0";
}

/** Create map and add it to the page. */
function initMap() {
  fetch('/travel').then(response => response.json()).then((locations) => {
    const map = new google.maps.Map(
          document.getElementById('map'),
          {center: {lat: 14.5994, lng: -28.67}, zoom: 2});
    setLandmarks(map, locations);
  });
}

/* set markers for locations on the map
   and add info window for each marker */
function setLandmarks(map, locations) {
    locations.forEach((location) => {
        const locationMarker = new google.maps.Marker({
           position: {lat: location.latitude, lng: location.longitude}, 
           map: map,
           title: location.city
        });

        // add info window containing a picture if image src is specified
        if(location.image) {
            const infoWindow = new google.maps.InfoWindow({content: location.image});
            locationMarker.addListener('click', () => {
                infoWindow.open(map, locationMarker);
            });
        }
    });
}

