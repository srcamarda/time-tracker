import { Footer } from '@/components/footer'
import { Header } from '@/components/header'
import { Link, useParams } from 'react-router-dom'
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog'
import { FiPlusCircle } from 'react-icons/fi'
import { TasksForm } from '@/components/form/tasks-form'

export function ProjectPage() {
  const { id } = useParams()

  return (
    <>
      <div className="mx-auto max-w-7xl px-4 py-12">
        <Header />

        <Link to={'/dashboard'} className="mt-4 inline-block hover:underline">
          ← Voltar a página inicial
        </Link>

        <h2 className="my-4 text-xl font-bold">Projeto X</h2>

        <div className="grid grid-cols-[repeat(2,auto)_25%] gap-6 rounded-2xl bg-white p-10">
          <div>
            <h3 className="font-bold text-davys-gray">Data de início</h3>
            <p>24 de abril de 2024</p>
          </div>

          <div>
            <h3 className="font-bold text-davys-gray">Data de término</h3>
            <p>Presente</p>
          </div>

          <div className="col-span-2 col-start-1 row-start-2">
            <h3 className="font-bold text-davys-gray">Descrição do Projeto</h3>
            <p className="text-justify">
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iste
              distinctio sed velit quis possimus autem ipsa perferendis
              doloremque corporis! Tempora ipsum quidem architecto id accusamus
              nesciunt officia iusto mollitia illum modi soluta explicabo
              consectetur quasi, commodi sed perferendis possimus fuga quisquam
              quia similique distinctio fugiat dolorem. Eius architecto quod
              totam?
            </p>
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

        <h2 className="my-4 text-xl font-bold">Tarefas</h2>

        <ul className="grid grid-cols-[repeat(auto-fill,15rem)] grid-rows-[repeat(auto-fill,8rem)] gap-8">
          <NewTaskButton />

          <TaskCard />
        </ul>
      </div>
      <Footer />
    </>
  )
}

function NewTaskButton() {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <li className="flex flex-col items-center justify-center gap-2 rounded-2xl bg-white hover:cursor-pointer">
          <FiPlusCircle className="size-12 text-peach" />
          <p>Adicionar Tarefa</p>
        </li>
      </DialogTrigger>
      <DialogContent className="max-h-[calc(100%_-_2rem)] w-[48rem] max-w-[calc(100%_-_2rem)] bg-white  px-10 pb-12 pt-8">
        <DialogHeader>
          <DialogTitle className="text-4xl">Adicionar Nova Tarefa</DialogTitle>
          <DialogDescription>
            <TasksForm />
          </DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  )
}

function TaskCard() {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <li className="flex h-full flex-col justify-center rounded-2xl bg-white px-6 py-4 hover:cursor-pointer">
          <h3 className="font-bold">Tarefa X</h3>

          <ul className="my-1">
            <li className="inline-block rounded bg-cerise p-1 text-xs text-white">
              URGENTE
            </li>
          </ul>

          <p className="text-xs">24/04/2024 - presente</p>

          <p className="text-xs">Paulo</p>
        </li>
      </DialogTrigger>
      <DialogContent className="max-h-[calc(100%_-_2rem)] w-[48rem] max-w-[calc(100%_-_2rem)] bg-white  px-10 pb-12 pt-8">
        <DialogHeader>
          <DialogTitle className="text-4xl">Atualizar Tarefa</DialogTitle>
          <DialogDescription>
            <TasksForm />
          </DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  )
}
