import { Footer } from "@/components/footer"
import { Header } from "@/components/header"
import { Link } from "react-router-dom"

export function Dashboard() {
  return (
    <>
      <div className="max-w-7xl px-4 py-12">
        <Header />

        <h2 className="text-xl font-bold text-dim-gray my-4">Projetos</h2>

        <ul className="grid grid-cols-[repeat(auto-fill,15rem)] grid-rows-[repeat(auto-fill,10rem)] gap-8">
          <li className="rounded-2xl bg-white flex justify-center items-center">
            <p>Adicionar Projeto</p>
          </li>

          <li className="rounded-2xl bg-white">
            <Link to={'/project/1'} className="flex flex-col justify-center px-6 h-full">
              <h3 className="font-bold">Projeto X</h3>

              <ul className="my-1">
                <li className="text-xs bg-cerise p-1 text-white inline-block rounded">URGENTE</li>
              </ul>

              <p className="text-xs">24/04/2024 - presente</p>

              <p className="text-xs">5 membros</p>

              <p className="text-xs">Tarefas concluídas: 5/10</p>
            </Link>
          </li>
        </ul>
      </div>
      <Footer />
    </>
  )
}