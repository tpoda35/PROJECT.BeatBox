import {createRoot} from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {AuthProvider} from "./auth/AuthContext.tsx";
import {ApiProvider} from "./api/ApiContext.tsx";
import {TrackProvider} from "./components/track/track/TrackContext.tsx";

createRoot(document.getElementById('root')!).render(
  // <StrictMode>
      <AuthProvider>
          <ApiProvider>
              <TrackProvider>
                  <App />
              </TrackProvider>
          </ApiProvider>
      </AuthProvider>
  // </StrictMode>,
)
