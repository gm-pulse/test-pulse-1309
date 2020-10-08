namespace core.Entidades
{
    public class Pedido
    {
        public int Id { get; set; }
        public float ValorTotal { get; set; }
        public float ValorDesconto{ get; set; }

        public virtual Cliente Cliente{ get; set; }
    }
}