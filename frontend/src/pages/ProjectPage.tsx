import { Footer } from "@/components/footer"
import { Header } from "@/components/header"
import { Link, useParams } from "react-router-dom"

export function ProjectPage() {
  const { id } = useParams()

  return (
    <>
      <div className="max-w-7xl px-4 py-12">
        <Header />

        <Link to={'/dashboard'} className="mt-4 inline-block hover:underline">← Voltar a página inicial</Link>

        <h2 className="text-xl font-bold my-4">Projeto X</h2>

        <div className="bg-white rounded-2xl p-10 grid grid-cols-[repeat(2,auto)_25%] gap-6">
          <div>
            <h3 className="font-bold text-davys-gray">Data de início</h3>
            <p>24 de abril de 2024</p>
          </div>

          <div>
            <h3 className="font-bold text-davys-gray">Data de término</h3>
            <p>Presente</p>
          </div>

          <div className="row-start-2 col-span-2 col-start-1">
            <h3 className="font-bold text-davys-gray">Descrição do Projeto</h3>
            <p className="text-justify">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iste distinctio sed velit quis possimus autem ipsa perferendis doloremque corporis! Tempora ipsum quidem architecto id accusamus nesciunt officia iusto mollitia illum modi soluta explicabo consectetur quasi, commodi sed perferendis possimus fuga quisquam quia similique distinctio fugiat dolorem. Eius architecto quod totam?</p>
          </div>

          <div className="row-span-2">
            <h3 className="font-bold text-davys-gray">Membros</h3>
            <ul>
              <li>Paulo</li>
              <li>Pedro</li>
              <li>Davi</li>
              <li>Edu</li>
              <li>Thiago</li>
            </ul>
          </div>
        </div>

        <h2 className="text-xl font-bold my-4">Tarefas</h2>

        <ul className="grid grid-cols-[repeat(auto-fill,15rem)] grid-rows-[repeat(auto-fill,8rem)] gap-8">
          <li className="rounded-2xl bg-white flex justify-center items-center">
            <p>Adicionar Tarefa</p>
          </li>

          <li className="rounded-2xl bg-white flex flex-col justify-center px-6 h-full">
            <h3 className="font-bold">Tarefa X</h3>

            <ul className="my-1">
              <li className="text-xs bg-cerise p-1 text-white inline-block rounded">URGENTE</li>
            </ul>

            <p className="text-xs">24/04/2024 - presente</p>

            <p className="text-xs">Paulo</p>
          </li>
        </ul>
      </div>
      <Footer />
    </>
  )
}