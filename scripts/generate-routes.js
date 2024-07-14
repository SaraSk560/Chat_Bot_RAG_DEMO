import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

// Get the directory name of the current module
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const routes = [
  {
    path: '/',
    component: 'Frontend/views/HomeView'
  },
  // add more routes here
];

const content = `const routes = ${JSON.stringify(routes, null, 2)};\n\nexport default routes;`;

fs.writeFileSync(path.join(__dirname, '../src/main/frontend/generated/file-routes.tsx'), content);

console.log('Routes generated successfully.');
