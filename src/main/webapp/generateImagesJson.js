const fs = require('fs');
const path = require('path');
// Chemin vers le dossier des images
const directoryPath = path.join(__dirname, 'content/images/carousel_sports');

// Chemin vers le fichier JSON
const jsonFilePath = path.join(__dirname, 'content/images/carousel_sports/images.json');

// Lire le contenu du dossier
fs.readdir(directoryPath, (err, files) => {
  if (err) {
    return console.log('Impossible de scanner le dossier : ' + err);
  }

  // Filtrer les fichiers pour ne garder que les images
  const images = files.filter(file => file.endsWith('.jpg') || file.endsWith('.png') || file.endsWith('.jpeg'));

  // Écrire la liste des images dans le fichier JSON
  fs.writeFile(jsonFilePath, JSON.stringify(images, null, 2), err => {
    if (err) {
      return console.log("Erreur lors de l'écriture du fichier JSON : " + err);
    }
    console.log('Fichier JSON des images mis à jour avec succès.');
  });
});
