import { Footer } from '@/components/footer'
import { Header } from '@/components/header'
import { Link } from 'react-router-dom'
import { FiPlusCircle } from 'react-icons/fi'
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog'
import { ProjectsForm } from '@/components/form/projects-form'

export function Dashboard() {
  return (
    <>
      <div className="mx-auto max-w-7xl px-4 py-12">
        <Header />

        <h2 className="my-4 text-xl font-bold text-dim-gray">Projetos</h2>

        <ul className="grid grid-cols-[repeat(auto-fill,15rem)] grid-rows-[repeat(auto-fill,10rem)] gap-8">
          <NewProjectButton />

          <ProjectCard />
        </ul>
      </div>
      <Footer />
    </>
  )
}

function NewProjectButton() {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <li className="flex flex-col items-center justify-center gap-2 rounded-2xl bg-white hover:cursor-pointer">
          <FiPlusCircle className="size-12 text-peach" />
          <p>Adicionar Projeto</p>
        </li>
      </DialogTrigger>
      <DialogContent className="max-h-[calc(100%_-_2rem)] w-[48rem] max-w-[calc(100%_-_2rem)] bg-white  px-10 pb-12 pt-8">
        <DialogHeader>
          <DialogTitle className="text-4xl">Adicionar Novo Projeto</DialogTitle>
          <DialogDescription>
            <ProjectsForm />
          </DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  )
}

function ProjectCard() {
  return (
    <li className="rounded-2xl bg-white">
      <Link
        to={'/project/1'}
        className="flex h-full flex-col justify-center px-6"
      >
        <h3 className="font-bold">Projeto X</h3>

        <ul className="my-1">
          <li className="inline-block rounded bg-cerise p-1 text-xs text-white">
            URGENTE
          </li>
        </ul>

        <p className="text-xs">24/04/2024 - presente</p>

        <p className="text-xs">5 membros</p>

        <p className="text-xs">Tarefas concluídas: 5/10</p>
      </Link>
    </li>
  )
}
