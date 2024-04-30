import { Link } from "react-router-dom";

export function Header() {
  return (
    <header className="flex justify-between items-center">
      <h1 className="text-4xl font-bold">Bem vindo(a) de volta!</h1>

      <nav>
        <ul className="flex gap-8">
          <li><Link to="/">Perfil</Link></li>
          <li><Link to="/">Sair</Link></li>
        </ul>
      </nav>
    </header>
  )
}